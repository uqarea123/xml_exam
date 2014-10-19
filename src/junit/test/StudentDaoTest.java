package junit.test;


import org.junit.Test;

import com.llq.dao.StudentDao;
import com.llq.domain.Student;

public class StudentDaoTest {

	@Test
	public void testAdd() throws Exception {
		
		StudentDao dao=new StudentDao();
		Student s=new Student();
		s.setExamid("121");
		s.setIdcard("121");
		s.setName("aa");
		s.setGrade(89);
		s.setLocation("北京");
		dao.add(s);
	}

}
