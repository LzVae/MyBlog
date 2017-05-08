package com.ssh.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ssh.model.Student;
import com.ssh.service.MessageService;
import com.ssh.service.ScoreService;
import com.ssh.service.StudentService;

import com.ssh.dao.ScoreDAO;
import com.ssh.model.Message;
import com.ssh.model.Score;

public class StudentAction extends ActionSupport implements ServletRequestAware,
															ServletResponseAware{
	
	private HttpSession session;
	private HttpServletRequest request; 
	private HttpServletResponse response;
	private ServletContext application;
	
	
	private Student student=new Student();
	private Score score=new Score();
	
	private StudentService studentService;
	private ScoreService scoreService;
	private MessageService messageService;
	
	
	//setter����
	public void setScore(Score score) {
		this.score = score;
	}
	
	public void setStudent(Student student) {
		this.student = student;
	}
	
	public void setScoreService(ScoreService scoreService) {
		this.scoreService = scoreService;
	}
	
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	
	
	
	//������servletAPI��ϵķ�ʽ��ȡԭ����session��request
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
	
	
	

	
	
	
	
	
	//�����Ǹ��ַ��񷽷�
	//��ѯ�û��Ƿ��Ѿ���ע��
		public void isRegist() throws IOException
		{
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out=response.getWriter();
			
			//��ȡ������
			String username=request.getParameter("username");
			
			System.out.println("�û���cao:"+username);
			
			if (!username.equals("")) 
			{
				List<Student> stu=this.studentService.findStudent(username);//���ز�ѯ�����
				//�ж��Ƿ�ע�Ტ������Ӧ��Ϣ
				if (!stu.isEmpty()) 
				{
					out.print("<font color='red'>�û����ѱ�ע�ᣡ</font>");
					System.out.println("�û����Ѿ���ע��!");
				} 
				else 
				{
					out.print("<font color='green'>����ʹ��</font>");
					System.out.println("�û���δע�ᣬ����ʹ��!");

				}
				
				
				
			} else {
				out.print("<font color='red'>�û�������Ϊ��!</font>");
				System.out.println("�û�������Ϊ��!");

			}
				
			
		}
	
	
	
		
		
		//ʵ���û���½
		public void Login() throws IOException
		{
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out=response.getWriter();
			
			//��ȡ������
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			
			
			//���ز�ѯ�����
			List<Student> stu=this.studentService.findStudent(username);
			
			//����û������ڣ����������Ƿ���ȷ
			if (!stu.isEmpty()) 
			{
				if (stu.get(0).getPassword().equals(password))
				{
					out.print("true");
//					this.session=request.getSession();
					session.setAttribute("student", stu.get(0));
					
					//��application�м��������˵Ķ�̬
					System.out.println("messages:"+messageService.findallMessage().get(0).getCreatetime());
					
					List<Message> messages=messageService.findallMessage();
					List<Message> mymessages=messageService.findMessagebystudent((Student) session.getAttribute("student"));
					Collections.sort(messages);
					Collections.sort(mymessages);
					
					
					application.setAttribute("messages",messages);
					session.setAttribute("mymessages", mymessages);
					
					System.out.println("��ת�ɹ�!");
					
					//�����û��score��ļ�¼���Ǿʹ���һ����¼
//					System.out.println("5555"+stu.get(0).getScore().getId());
				if (stu.get(0).getScore() == null) {
						
//						
						score.setStudent(stu.get(0));
						score.setIsscored((long) 0);
						
						scoreService.saveScore(score);
						
						session.setAttribute("score", score);
					}
					
					else
					{
						session.setAttribute("score", stu.get(0).getScore());
					}
					
					
					
					
					
				} 
				else 
				{
					out.print("<font color='red'>�û������������!</font>");
					System.out.println("2�û������������!");
				}
				
				
			} 
			
			else 
			{
				out.print("<font color='red'>�û���������!</font>");
				System.out.println("1�û���������!");
			}
			
			
			
		}
		
		
		
		
		
		//��ѯ�Ƿ��û��Ƿ��Ѿ���¼
		public void isLogin() throws IOException
		{
			//��servletAPI��ϵķ�ʽ����ȡrequest��response����

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out=response.getWriter();
			
			//��ȡsession
//			this.session=request.getSession();
			if (session.getAttribute("student")==null) {
				out.print("false");
			}
			
			else
			{
				out.print("true");
			}
			
		}
		
		
		
		
		
		
		
		//ʵ���˳���¼
		public String  Logout()
		{
			
			session.removeAttribute("student");
			session.removeAttribute("score");
			session.removeAttribute("mymessages");
			
			System.out.println("һλ�û��˳�");
			return "logoutsuccess";
		}
		
		
		
		
		
		//ʵ���޸�����
		public void Changepassowrd() throws IOException
		{

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out=response.getWriter();
			
			//��ȡ������
			String password=request.getParameter("password1");
			//��ȡsession
//			HttpSession session=request.getSession();
			//ȡ��session�е��û�
			Student student =(Student) session.getAttribute("student");
			student.setPassword(password);
			
			
			this.studentService.updateStudent(student);
			
			session.removeAttribute("student");
			session.setAttribute("student", student);
			
			out.print("true");
				
				
		}
		
		
		
		//ʵ��ע�Ṧ��
		public String Regist() throws IOException
		{
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out=response.getWriter();
			
			//��ȡ������
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			String name=request.getParameter("name");
			String sex=request.getParameter("sex");
			
			
			//��ֹ���˶������ǰ�˴����ظ�ע�ᣬ�ں�̨Ҳ����У�����
			
			
			List<Student> stucheck=studentService.findStudent(username);//���ز�ѯ�����
			//�ж��Ƿ�ע�Ტ������Ӧ��Ϣ
			if (!stucheck.isEmpty()) 
			{
//				out.print("<font color='red'>�û����ѱ�ע�ᣡ</font>");
				System.out.println("223�û����Ѿ���ע��!");
				return "registfail";
			} 
			else 
			{

				
				//�ṹ����ע���ʵ����
				System.out.println("username233:"+username);
				this.student.setUsername(username);
				this.student.setPassword(password);
				this.student.setName(name);
				this.student.setSex(sex);
				
				
				
				score.setStudent(student);
				score.setIsscored((long) 0);
				
				
				
				
				
				scoreService.saveScore(score);
				//��Ϊ�Ǽ������棬���Ա���score��ʱ����Զ�����student
//				studentService.saveStudent(student);
				
				System.out.println("ע��ɹ�");
				
				
				
				session.setAttribute("score", score);
				session.setAttribute("student", student);
				
				return "registsuccess";
				

			}
		}

}
