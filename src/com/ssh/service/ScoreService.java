package com.ssh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ssh.dao.ScoreDAO;
import com.ssh.model.Score;
import com.ssh.model.Student;

@Component
public class ScoreService {
	
	
	@Autowired
	private ScoreDAO scoreDAO;
	
	
	//�����Ǹ��ַ��񷽷�
	
	//ʵ�ֱ�������Ĺ���
	public void saveScore(Score score)
	{
		this.scoreDAO.SaveScore(score);
	}
	
	
	//ͨ��ѧ�����Ҹ�ѧ���ķ���
	public List<Score> findScorebyStudent(Student student)
	{
		return this.scoreDAO.FindScoreByStudent(student);
	}
	
	
	//ʵ���ύ�����Ĺ���
	public void updateScore(Score score)
	{
		this.scoreDAO.UpdateScore(score);
		
	}
	
	
	
	
}
