package com.llq.dao;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.llq.domain.Student;
import com.llq.exception.StudentNotExistException;
import com.llq.utils.XmlUtils;


public class StudentDao {

	/*
	 <student examid="222" idcard="111">
		<name>张三</name>
		<location>沈阳</location>
		<grade>89</grade>
	</student>

	 */
	
	/**
	 * 添加学生
	 * @param student
	 */
	public void add(Student student){
		try{
			Document document = XmlUtils.getDocument();

			//创建出封装学生信息的标签
			Element student_node = document.createElement("student");
			student_node.setAttribute("examid", student.getExamid());
			student_node.setAttribute("idcard", student.getIdcard());
			
			//创建出封装学生姓名、所在地、成绩的标签
			Element name = document.createElement("name");
			name.setTextContent(student.getName());
			
			Element location = document.createElement("location");
			location.setTextContent(student.getLocation());
			
			Element grade = document.createElement("grade");
			grade.setTextContent(student.getGrade()+"");
			
			student_node.appendChild(name);
			student_node.appendChild(location);
			student_node.appendChild(grade);
			
			//把封装了学生信息的标签挂到文档上
			document.getElementsByTagName("exam").item(0).appendChild(student_node);
			
			XmlUtils.write2Xml(document);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 根据标签name来删除对应的学生
	 * @param name
	 * @throws StudentNotExistException 
	 */
	public void delete(String name) throws StudentNotExistException{
		
		try {
			Document document = XmlUtils.getDocument();
			NodeList nodeList=document.getElementsByTagName("name");
			for (int i = 0; i < nodeList.getLength(); i++) {
				if(nodeList.item(i).getTextContent().equals(name)){
					nodeList.item(i).getParentNode().getParentNode().removeChild(nodeList.item(i).getParentNode());
					XmlUtils.write2Xml(document);
					return;
				}
			}
			
			throw new StudentNotExistException(name+"不存在！");
		}catch (StudentNotExistException e) {
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * 根据examid来查找学生信息
	 * @param examid
	 * @return
	 */
	public Student find(String examid){
		
		try {
			Document document = XmlUtils.getDocument();
			NodeList nodeList=document.getElementsByTagName("student");
			for(int i=0;i<nodeList.getLength();i++){
				Element student_tag=(Element) nodeList.item(i);
				if(student_tag.getAttribute("examid").equals(examid)){
					//找到与examid相匹配的学生，new出一个学生对象来封装这个学生的信息返回
					Student s=new Student();
					s.setExamid(examid);
					s.setIdcard(student_tag.getAttribute("idcard"));
					s.setName(student_tag.getElementsByTagName("name").item(0).getTextContent());
					s.setLocation(student_tag.getElementsByTagName("location").item(0).getTextContent());
					s.setGrade(Double.parseDouble(student_tag.getElementsByTagName("grade").item(0).getTextContent()));
					return s;
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
