package dao;

import java.util.Calendar;

import model.AuthorCandidate;
import model.Event;
import model.Item;
import model.UserCandidate;
public class DAOtest {

	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		String s ="131";
		int n=246;
		EventDAO dao = new EventDAO();
		Event event= new Event(s,s,s,s,s,s,c,c,c,c,n,n);
		Item item = new Item(s,s,s,s,s,c);
		UserCandidate uc = new UserCandidate(s,s,s,s,n);
		AuthorCandidate ac = new AuthorCandidate(s,s,c,s);
		for(int i=0;i<5000;i++){
		dao.insertAuthorCandidateList(ac);
		}
	}

}
