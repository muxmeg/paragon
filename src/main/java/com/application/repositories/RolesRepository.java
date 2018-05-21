package com.application.repositories;

import com.application.model.Role;
import com.application.model.RoleParameter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
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
    private static final String FIND_ROLES_NAMES = "SELECT name FROM roles";
    private static final String UPDATE_ROLE_PASSWORD = "UPDATE roles SET password = :newPassword, secret = :isSecret " +
            "WHERE name = :roleName";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RoleRowMapper rowMapper;
    private final RoleParameterRowMapper roleParameterRowMapper;

    public RolesRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        rowMapper = new RoleRowMapper();
        roleParameterRowMapper = new RoleParameterRowMapper();
    }

    public Optional<Role> getRole(@NotNull String name) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(GET_ROLE, new MapSqlParameterSource("name", name),
                    rowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<String> findRoleNames(String team) {
        StringBuilder query = new StringBuilder(FIND_ROLES_NAMES);
        MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
        if (team != null) {
            query.append(" WHERE team = :team");
            sqlParameters.addValue("team", team);
        }
        return jdbcTemplate.queryForList(query.toString(), sqlParameters, String.class);
    }

    public List<RoleParameter> findRolesParameters(@NotNull String roleName) {
        return jdbcTemplate.query(FIND_ROLE_PARAMETERS, new MapSqlParameterSource("roleName", roleName),
                roleParameterRowMapper);
    }

    public void increaseOrSetParameter(@NotNull String role, @NotNull String parameter, int value) {
        MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
        sqlParameters.addValue("roleName", role);
        sqlParameters.addValue("name", parameter);
        sqlParameters.addValue("value", value);
        jdbcTemplate.update(INSERT_OR_INCREASE_PARAMETER, sqlParameters);
    }

    public void changePassword(@NotNull String role, @NotNull String password, boolean isSecret) {
        MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
        sqlParameters.addValue("roleName", role);
        sqlParameters.addValue("newPassword", password);
        sqlParameters.addValue("isSecret", isSecret);
        jdbcTemplate.update(UPDATE_ROLE_PASSWORD, sqlParameters);
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
