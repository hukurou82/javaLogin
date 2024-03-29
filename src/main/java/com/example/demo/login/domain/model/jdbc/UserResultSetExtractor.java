package com.example.demo.login.domain.model.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.example.demo.login.domain.model.User;

//ポイント：ResultSetExtractor
public class UserResultSetExtractor implements ResultSetExtractor<List<User>> {

	@Override
	public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {

		//User格納用LIST
		List<User> userList = new ArrayList<>();

		//取得件数分のloop
		while(rs.next()) {

			//Listに追加するインスタンスの生成
			User user = new User();

			//取得したレコードをUserインスタンスにセット
			user.setUserId(rs.getString("user_id"));
			user.setPassword(rs.getString("password"));
			user.setUserName(rs.getString("user_name"));
			user.setBirthday(rs.getDate("Birthday"));
			user.setAge(rs.getInt("age"));
			user.setMarriage(rs.getBoolean("marriage"));
			user.setRole(rs.getString("role"));

			//ListにUserを追加
			userList.add(user);
		}

		//1件もなかった場合例外を投げる
		if(userList.size() == 0) {
			throw new EmptyResultDataAccessException(1);
		}

		return userList;
	}
}
