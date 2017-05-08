package com.ssh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ssh.dao.StudentDAO;
import com.ssh.model.Student;

@Component
public class StudentService {

	@Autowired
	private StudentDAO studentDAO;
	
	public void setStudentDAO(StudentDAO studentDAO) {
		this.studentDAO = studentDAO;
	}
	
	public StudentDAO getStudentDAO() {
		return studentDAO;
	}
	
	
	//����дservice���񷽷�
	
	//1.�����û�����ѯѧ��������ֵ��Student�����һ��list,����Ϊusername
	public List<Student> findStudent(String username)
	{
		System.out.println("�û���fuck:"+username);
		System.out.println("studentDAO:"+studentDAO);
		return this.studentDAO.FindStudentByUsername(username);
	}
	
	
	//2.����һ��ѧ���û����������Ϊһ��student����
	public void saveStudent(Student student)
	{
		this.studentDAO.SaveStudent(student);
	}
	
	//3.����ѧ����Ϣ���������Ϊstudent����
	public void updateStudent(Student student)
	{
		this.studentDAO.UpdateStudent(student);
	}
	
	//4.��ѯδ��ֵ�ѧ��������ֵΪList<Oblect[]>
	public List<Object []> findnotscoredStudent()
	{
		return this.studentDAO.FindNotscoredStudent();
	}
	
	//5.��ѯ�Ѵ�ֵ�ѧ��������ֵΪList<Oblect[]>
	public List<Object []> findhadscoredStudent()
	{
		return this.studentDAO.FindHadscoredStudent();
	}
	
	//6.��ѯ�Ѵ��ѧ��������
	public int stu_Scored()
	{
		return this.studentDAO.Stu_Scored();
	}
	
	
	//7.��ѯƽ���֣�����Ĳ���Ϊְ�����д,��೤:bz
	public double[] averageScore(String position)
	{
		return this.studentDAO.AverageScore(position);
	}
	
}
