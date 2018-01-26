package com.application.repositories;

import com.application.model.Direction;
import com.application.model.Ship;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@Transactional
public class ShipRepository {
    private static final String GET_SHIP = "SELECT * FROM ship";
    private static final String UPDATE_SHIP = "UPDATE ship SET hull = :hull, air = :air, engine = :engine," +
            " coordX = :coordX, coordY = :coordY, speed = :speed, direction = :direction";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ShipRowMapper rowMapper;
    private Ship cachedShip;

    public ShipRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = new ShipRowMapper();
    }

    public Ship getShip() {
        if (cachedShip == null) {
            cachedShip = jdbcTemplate.queryForObject(GET_SHIP, new MapSqlParameterSource(), rowMapper);
        }
        return cachedShip.copy();
    }

    public boolean isTransmitterEnabled() {
        if (cachedShip == null) {
            cachedShip = jdbcTemplate.queryForObject(GET_SHIP, new MapSqlParameterSource(), rowMapper);
        }
        return cachedShip.isTransmitterEnabled();
    }

    public void updateShip(Ship ship) {
        cachedShip = ship.copy();
        jdbcTemplate.update(UPDATE_SHIP, new MapSqlParameterSource().addValue("hull", ship.getHull())
                .addValue("air", ship.getAir()).addValue("engine", ship.getEngine())
                .addValue("coordX", ship.getCoordX()).addValue("coordY", ship.getCoordY())
                .addValue("speed", ship.getSpeed()).addValue("direction", ship.getDirection().ordinal()));
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
                    .build();
        }
    }
}
