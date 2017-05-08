package com.ssh.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ssh.model.Message;
import com.ssh.model.Student;
import com.ssh.service.MessageService;

public class PublishMessageAction extends ActionSupport implements ServletRequestAware
																	,ServletResponseAware{
	
	
	private HttpSession session;
	private HttpServletRequest request; 
	private HttpServletResponse response;
	private ServletContext application;
	
	private MessageService messageService;
	
	private Message message=new Message();
	
	
	
	//setter����
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	
	
	
	
	
	
	
	
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		this.response=arg0;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		this.request=arg0;
		//˳���ȡsession
		this.session=request.getSession();
		this.application=session.getServletContext();
	}
	
	
	
	public void PublishNewMessage() throws IOException
	{
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		//��ȡ������
		String content=request.getParameter("content");
		
		this.message.setContent(content);
		this.message.setStudent((Student) session.getAttribute("student"));
		this.messageService.saveMessage(message);
		
		List<Message> messages=messageService.findallMessage();
		List<Message> mymessages=messageService.findMessagebystudent((Student) session.getAttribute("student"));
		Collections.sort(messages);
		Collections.sort(mymessages);
		
		application.removeAttribute("messages");
		application.setAttribute("messages", messages);
		
		session.removeAttribute("mymessages");
		session.setAttribute("mymessages", mymessages);
		out.println("true");
	}
	
	
	public void DeleteMessage() throws IOException
	{
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		//��ȡҪɾ������Ϣ��id
		Long id=Long.valueOf(request.getParameter("del_id"));
		this.message=this.messageService.findMessagebyid(id);
		this.messageService.deleteMessage(message);
		
		
		
		//����application
		List<Message> messages=messageService.findallMessage();
		List<Message> mymessages=messageService.findMessagebystudent((Student) session.getAttribute("student"));
		Collections.sort(messages);
		Collections.sort(mymessages);
		
		application.removeAttribute("messages");
		application.setAttribute("messages", messages);
		
		session.removeAttribute("mymessages");
		session.setAttribute("mymessages", mymessages);
		
		out.println(String.valueOf(id));
	}
	
	
	
	
	

}
