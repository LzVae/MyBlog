package com.ssh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ssh.dao.MessageDAO;
import com.ssh.model.Message;
import com.ssh.model.Student;

@Component
public class MessageService {
	
	@Autowired
	private MessageDAO messageDAO;

	public MessageDAO getMessageDAO() {
		return messageDAO;
	}

	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}
	
	//������service�ķ��񷽷�
	//1.�־û�һ����̬��message��
	public void saveMessage(Message message)
	{
		this.messageDAO.SaveMessage(message);
	}
	
	
	//2.��ѯ���еĶ�̬
	public List<Message> findallMessage()
	{
		return this.messageDAO.FindAllMessage();
	}
	
	//3.��ѯĳһλ�û������ж�̬
	public List<Message> findMessagebystudent(Student student)
	{
		return this.messageDAO.FindMessageByStudent(student);
	}
	
	
	//4.����id������һ��message
	public Message findMessagebyid(Long id)
	{
		return this.messageDAO.FindMessageById(id);
	}
	
	
	//5.ɾ��һ��message
	public void deleteMessage(Message message)
	{
		this.messageDAO.DeleteMessage(message);
	}
	
	
	
	
	
}
