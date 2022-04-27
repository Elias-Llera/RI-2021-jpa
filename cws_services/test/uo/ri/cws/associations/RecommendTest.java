package uo.ri.cws.associations;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import uo.ri.cws.domain.Associations;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.Recommendation;

public class RecommendTest {
	
	private Client sponsor1;
	private Client rec1;
	private Client rec2;
	
	private Recommendation r1;
	private Recommendation r2;

	@Before
	public void setUp() {
		sponsor1 = new Client("111", "Tania", "Tabárez");
		
		rec1 = new Client("222", "Jose", "Jiménez");
		rec2 = new Client("333", "Alba", "Álvarez");
		
		r1 = new Recommendation(sponsor1, rec1);
		r2 = new Recommendation(sponsor1, rec2);	
	}
	
	@Test
	public void linkOnRecommend() {
		assertTrue(r1.getRecommended().equals(rec1));
		assertTrue(r2.getRecommended().equals(rec2));
		
		assertTrue(r1.getSponsor().equals(sponsor1));
		assertTrue(r2.getSponsor().equals(sponsor1));
		
		assertTrue(sponsor1.getSponsored().contains(r1));
		assertTrue(sponsor1.getSponsored().contains(r2));
		
		assertTrue(rec1.getRecommended().equals(r1));
		assertTrue(rec2.getRecommended().equals(r2));
	}
	
	@Test
	public void unlinkOkRecommend() {
		
		Associations.Recommend.unlink(r1);
		Associations.Recommend.unlink(r2);
		
		assertTrue(r1.getRecommended() == null);
		assertTrue(r2.getRecommended() == null);
		
		assertTrue(r1.getSponsor() == null);
		assertTrue(r2.getSponsor() == null);
		
		assertTrue(sponsor1.getSponsored().isEmpty());
		
		assertTrue(rec1.getRecommended() == null);
		assertTrue(rec2.getRecommended() == null);
	}
	
	@Test
	public void testSafeReturn() {
		Set<Recommendation> sponsored = sponsor1.getSponsored();
		sponsored.remove(r1);
		
		assertTrue(sponsored.size() == 1);
		assertTrue( "It must be a copy of the collection", 
				sponsor1.getSponsored().size() == 2
			);
		
	}
	
	

}
