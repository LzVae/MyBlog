package com.ssh.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ssh.model.Score;
import com.ssh.model.Student;

@Component
public class ScoreDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getSession()
	{
		return this.sessionFactory.getCurrentSession();
	}
	
	
	
	//������DAO�ķ���
	//1.ʵ�ֱ�������Ĺ��ܣ����������һ��Score����
	public void SaveScore(Score score)
	{
		this.getSession().save(score);
		
	}
	
	
	//2.ʵ��ͨ��ѧ����ѯ�����Ĺ��ܣ����������һ��Student���󣬣���ʵֻҪstudent�������id��
	public List<Score> FindScoreByStudent(Student student)
	{
		String hql="from Score c where c.student.id=?";
		
		return this.getSession().createQuery(hql).setLong(0, student.getId()).list();
		
	}
	
	//3.ʵ�ִ�ֹ���,����Ĳ���Ϊһ��Score����
	public void UpdateScore(Score score)
	{
		this.getSession().update(score);
	}
	

}
