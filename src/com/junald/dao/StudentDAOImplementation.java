package com.junald.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.junald.model.Student;
import com.junald.util.DBUtil;

public class StudentDAOImplementation implements StudentDAO {
	
	private Connection conn;

	public StudentDAOImplementation() {
		conn = DBUtil.getConnection();
	}
	@Override
	public void addStudent( Student student ) {
		try {
			String query = "insert into student (firstName, lastName, age) values (?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement( query );
			preparedStatement.setString( 1, student.getFirstName() );
			preparedStatement.setString( 2, student.getLastName() );
			preparedStatement.setInt( 3, student.getAge() );
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void deleteStudent( int studentId ) {
		try {
			String query = "delete from student where studentId=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, studentId);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void updateStudent( Student student ) {
		try {
			String query = "update student set firstName=?, lastName=?, age=? where studentId=?";
			PreparedStatement preparedStatement = conn.prepareStatement( query );
			preparedStatement.setString( 1, student.getFirstName() );
			preparedStatement.setString( 2, student.getLastName() );
			preparedStatement.setInt( 3, student.getAge() );
			preparedStatement.setInt(4, student.getStudentId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public List<Student> getAllStudents() {
		List<Student> students = new ArrayList<Student>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery( "select * from student" );
			while( resultSet.next() ) {
				Student student = new Student();
				student.setStudentId( resultSet.getInt( "studentId" ) );
				student.setFirstName( resultSet.getString( "firstName" ) );
				student.setLastName( resultSet.getString( "lastName" ) );
				student.setAge( resultSet.getInt( "age" ) );
				students.add(student);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}
	@Override
	public Student getStudentById(int studentId) {
		Student student = new Student();
		try {
			String query = "select * from student where studentId=?";
			PreparedStatement preparedStatement = conn.prepareStatement( query );
			preparedStatement.setInt(1, studentId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while( resultSet.next() ) {
				student.setStudentId( resultSet.getInt( "studentId" ) );
				student.setFirstName( resultSet.getString( "firstName" ) );
				student.setLastName( resultSet.getString( "LastName" ) );
				student.setAge( resultSet.getInt( "age" ) );
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

}