package com.application.repositories;

import com.application.model.Role;
import com.application.model.RoleParameter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class RolesRepository {
    private static final String GET_ROLE = "SELECT * FROM roles WHERE name = :name";
    private static final String FIND_ROLE_PARAMETERS = "SELECT * FROM role_parameters WHERE roleName = :roleName";
    private static final String GET_ROLE_PARAMETER = "SELECT * FROM role_parameters WHERE roleName = :roleName AND " +
            "name = :name";
    private static final String INSERT_OR_INCREASE_PARAMETER = "INSERT INTO ROLE_PARAMETERS (ROLENAME, NAME, VALUE) " +
            "VALUES (:roleName, :name, :value) ON DUPLICATE KEY UPDATE VALUE = VALUE+1";
    private static final String FIND_ROLES_NAMES_BY_TEAM = "SELECT name FROM roles WHERE team = :team";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RoleRowMapper rowMapper;
    private final RoleParameterRowMapper roleParameterRowMapper;

    public RolesRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        rowMapper = new RoleRowMapper();
        roleParameterRowMapper = new RoleParameterRowMapper();
    }

    public Optional<Role> getRole(String name) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(GET_ROLE, new MapSqlParameterSource("name", name),
                    rowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<String> findRoleNamesByTeam(String team) {
        return jdbcTemplate.queryForList(FIND_ROLES_NAMES_BY_TEAM, new MapSqlParameterSource("team", team),
                String.class);
    }

    public List<RoleParameter> findRolesParameters(String roleName) {
        return jdbcTemplate.query(FIND_ROLE_PARAMETERS, new MapSqlParameterSource("roleName", roleName),
                roleParameterRowMapper);
    }

    public void increaseOrSetParameter(String role, String parameter, int value) {
        MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
        sqlParameters.addValue("roleName", role);
        sqlParameters.addValue("name", parameter);
        sqlParameters.addValue("value", value);
        jdbcTemplate.update(INSERT_OR_INCREASE_PARAMETER, sqlParameters);
    }

    private class RoleRowMapper implements RowMapper<Role> {
        @Override
        public Role mapRow(ResultSet resultSet, int i) throws SQLException {
            Role role = Role.builder().name(resultSet.getString("name"))
                    .password(resultSet.getString("password"))
                    .secret(resultSet.getBoolean("secret"))
                    .team(resultSet.getString("team"))
                    .build();
            role.setRoleParameters(findRolesParameters(role.getName()));
            return role;
        }
    }

    private class RoleParameterRowMapper implements RowMapper<RoleParameter> {
        @Override
        public RoleParameter mapRow(ResultSet resultSet, int i) throws SQLException {
            return RoleParameter.builder().name(resultSet.getString("name"))
                    .roleName(resultSet.getString("roleName"))
                    .value(resultSet.getString("value"))
                    .build();
        }
    }
}
