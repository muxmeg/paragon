package com.application.repositories;

import com.application.model.Direction;
import com.application.model.Ship;
import com.application.services.EventLoggingService;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Repository
@Transactional
public class ShipRepository {
    private static final String GET_SHIP = "SELECT * FROM ship";
    private static final String UPDATE_SHIP = "UPDATE ship SET hull = :hull, air = :air, engine = :engine," +
            " coordX = :coordX, coordY = :coordY, speed = :speed, direction = :direction, cargo = :cargo," +
            "transmitter_disabled_turns = :transmitterDisabledTurns, air_users = :airUsers, anchor_on = :anchorOn";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ShipRowMapper rowMapper;
    private final EventLoggingService eventLoggingService;
    private Ship cachedShip;

    public ShipRepository(NamedParameterJdbcTemplate jdbcTemplate, EventLoggingService eventLoggingService) {
        this.jdbcTemplate = jdbcTemplate;
        this.eventLoggingService = eventLoggingService;
        this.rowMapper = new ShipRowMapper();
        cachedShip = jdbcTemplate.queryForObject(GET_SHIP, new MapSqlParameterSource(), rowMapper);
    }

    public Ship getShip() {
        return cachedShip.copy();
    }

    public boolean isTransmitterEnabled() {
        return cachedShip.getTransmitterDisabledTurns() > 0;
    }

    public void updateShip(Ship ship) {
        cachedShip = ship.copy();
        jdbcTemplate.update(UPDATE_SHIP, new MapSqlParameterSource().addValue("hull", ship.getHull())
                .addValue("air", ship.getAir()).addValue("engine", ship.getEngine())
                .addValue("coordX", ship.getCoordX()).addValue("coordY", ship.getCoordY())
                .addValue("speed", ship.getSpeed()).addValue("direction", ship.getDirection().ordinal())
                .addValue("cargo", Arrays.stream(ship.getCargo()).map(String::valueOf)
                        .collect(Collectors.joining(",")))
                .addValue("transmitterDisabledTurns", ship.getTransmitterDisabledTurns())
                .addValue("airUsers", ship.getAirUsers()).addValue("anchorOn", ship.isAnchorOn()));
    }

    private class ShipRowMapper implements RowMapper<Ship> {

        @Override
        public Ship mapRow(ResultSet resultSet, int i) throws SQLException {

            return Ship.builder().air(resultSet.getInt("air"))
                    .hull(resultSet.getInt("hull"))
                    .engine(resultSet.getInt("engine"))
                    .coordX(resultSet.getInt("coordX"))
                    .coordY(resultSet.getInt("coordY"))
                    .speed(resultSet.getInt("speed"))
                    .direction(Direction.values()[resultSet.getInt("direction")])
                    .cargo(Arrays.stream(resultSet.getString("cargo").split(","))
                            .map(Boolean::parseBoolean).toArray(Boolean[]::new))
                    .airUsers(resultSet.getInt("air_users"))
                    .build();
        }
    }
}
