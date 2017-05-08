package com.ssh.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mchange.v2.async.StrandedTaskReporting;
import com.ssh.model.Student;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.TypeHost;

@Component
public class StudentDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getSession()
	{
		//
		System.out.println("sessionFactory1:"+sessionFactory);
		return this.sessionFactory.getCurrentSession();
	}
	
	
	//����дdao�ķ���
	
	//1.�����û�����ѯѧ��������ֵ��Student�����һ��list
	public List<Student> FindStudentByUsername(String username)
	{
		String hql="from Student s where s.username=?";
		//
		System.out.println("session1:"+this.getSession());
		
		Query query=getSession().createQuery(hql);
		query.setString(0, username);
		List<Student> result=query.list();
		
		return result;
		
	}
	
	//2.����һ��ѧ���û����������Ϊһ��student����
	public void SaveStudent(Student student)
	{
		 this.getSession().save(student);
	}
	
	
	//3.����ѧ����Ϣ���������Ϊstudent����
	public void UpdateStudent(Student student)
	{
		this.getSession().update(student);
	}
	
	
	//4.��ѯδ��ֵ�ѧ��������ֵΪList<Oblect[]>
	public List<Object[]> FindNotscoredStudent()
	{
		String sql="select distinct "+
				"dq1401.* from dq1401,score where (dq1401.id=score.uid and score.isscored=0)or(dq1401.id not in (select uid from score))";
		
		return this.getSession().createSQLQuery(sql).list();
	}
	
	//5.��ѯ�Ѵ�ֵ�ѧ��������ֵΪList<Oblect[]>
	
	public List<Object[]> FindHadscoredStudent()
	{
		String sql="select distinct "+
				"dq1401.* from dq1401,score where dq1401.id=score.uid and score.isscored=1";
		
		return this.getSession().createSQLQuery(sql).list();
	}
	
	
	//6.��ѯ�Ѿ��ж���ѧ�������

	public int Stu_Scored()
	{
		
		String sql="select count(isscored) from score where isscored=1";
		List<Object []> res= this.getSession().createSQLQuery(sql).list();
		
		return Integer.parseInt(String.valueOf(res.get(0)));
		
	}
	
	
	//7.��ѯƽ���֣�����Ĳ���Ϊְ�����д,��೤:bz
	public double[] AverageScore(String position)
	{
		String sql_sum="select sum("+position+"_1),sum("+position+"_2),sum("+position+"_3),sum("+position+"_4), sum("+position+"_5) "+
				"from score where isscored=1";
		
		Query query_sum=this.getSession().createSQLQuery(sql_sum);
		List<Object[]> result_sum=query_sum.list();
		int sum=this.Stu_Scored();
		
		double[] ave=new double[5];
		
		for (int i = 0; i < result_sum.get(0).length; i++) {
			
			ave[i]=Double.valueOf(result_sum.get(0)[i].toString())
					/sum;
			
		}
		
		return ave;
		
	}
	


}
