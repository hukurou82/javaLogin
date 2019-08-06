package com.example.demo.login.domain.model.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.model.repository.UserDao;

@Repository("UserDaoJdbcImpl")
public class UserDaoJdbcImpl implements UserDao{

	@Autowired
	JdbcTemplate jdbc;

	@Autowired
	PasswordEncoder passwordEncoder;

	//Userテーブルの件数を取得
	@Override
	public int count() throws DataAccessException{
		int count = jdbc.queryForObject("SELECT COUNT(*) FROM m_user", Integer.class);
		return count;
	}

	//Userテーブルにデータを一件取得
	@Override
	public int insertOne(User user) throws DataAccessException{

		//パスワード暗号化
		String password = passwordEncoder.encode(user.getPassword());
		//一件登録
		String sql = "INSERT INTO m_user("
			+ "user_id,"
			+ "password,"
			+ "user_name,"
			+ "birthday,"
			+ "age,"
			+ "marriage,"
			+ "role)"
			+ "VALUES(?,?,?,?,?,?,?)";

		//一見追加
		int rowNumber = jdbc.update(sql
			,user.getUserId()
			,password
			,user.getPassword()
			,user.getUserName()
			,user.getBirthday()
			,user.getAge()
			,user.isMarriage()
			,user.getRole());
		return rowNumber;
	}

	//Userテーブルのデータを一件取得
	@Override
	public User selectOne(String userId) throws DataAccessException{

		// １件取得
        Map<String, Object> map = jdbc.queryForMap("SELECT * FROM m_user"
                + " WHERE user_id = ?", userId);

        // 結果返却用の変数
        User user = new User();

        // 取得したデータを結果返却用の変数にセットしていく
        user.setUserId((String) map.get("user_id")); //ユーザーID
        user.setPassword((String) map.get("password")); //パスワード
        user.setUserName((String) map.get("user_name")); //ユーザー名
        user.setBirthday((Date) map.get("birthday")); //誕生日
        user.setAge((Integer) map.get("age")); //年齢
        user.setMarriage((Boolean) map.get("marriage")); //結婚ステータス
        user.setRole((String) map.get("role")); //ロール

        return user;
    }

	//Userテーブルの全テータ取得
	@Override
	public List<User> selectMany() throws DataAccessException{

		//複数件のselect
		//M_USERテーブルの全データを取得
		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM m_user");

		//結果返却用変数
		List<User>userList=new ArrayList<>();

		//取得したデータを結果返却用のListに格納
		for(Map<String, Object>map:getList) {

			//Userインスタンス生成
			User user=new User();

			//Userインスタンスに取得したデータをセット
			user.setUserId((String)map.get("user_id"));
			user.setPassword((String)map.get("password"));
			user.setUserName((String)map.get("user_name"));
			user.setBirthday((Date)map.get("birthday"));
			user.setAge((Integer)map.get("age"));
			user.setMarriage((Boolean)map.get("marriage"));
			user.setRole((String)map.get("role"));

			userList.add(user);
		}
	return userList;
	}

	//Userテーブルのデータを一件更新
	@Override
	public int updateOne(User user) throws DataAccessException{

		//パスワード暗号化
		String password = passwordEncoder.encode(user.getPassword());
		//1件更新
		String sql= "UPDATE M_USER"
			+ " SET"
			+ " password = ?,"
			+ " user_name = ?,"
			+ " birthday = ?,"
			+ " age = ?,"
			+ " marriage = ?"
			+ " WHERE user_id = ?";

			//一件更新
			int rowNumber = jdbc.update(sql
			,password
			,user.getPassword()
			,user.getUserName()
			,user.getBirthday()
			,user.getAge()
			,user.isMarriage()
			,user.getUserId());

		/*
		//トランザクション確認のため、わざと例外をスローする
		if(rowNumber > 0 ) {
			throw new DataAccessException("トランザクションテスト") {};
		}
		*/
		return rowNumber;
	}

	//Userテーブルを一件削除
	@Override
	public int deleteOne(String userId) throws DataAccessException{
		int rowNumber = jdbc.update("DELETE FROM m_user WHERE user_id = ?", userId);

		return rowNumber;
	}

	//Userテーブルの全データをCSVに出力する
	@Override
	public void userCsvOut() throws DataAccessException{

		//M_USERテーブルのデータを全件取得するSQL
		String sql = "SELECT * FROM m_user";

		//ResultSetExtractorの生成
		UserRowCallbackHandler handler = new UserRowCallbackHandler();

		//SQL実行＆CSV出力
		jdbc.query(sql,handler);

	}
}
