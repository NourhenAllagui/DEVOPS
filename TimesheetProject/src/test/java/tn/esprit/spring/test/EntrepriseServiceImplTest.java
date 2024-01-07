package tn.esprit.spring.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.EntrepriseServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntrepriseServiceImplTest {
private static final Logger l = LogManager.getLogger(EntrepriseServiceImplTest.class);
	
	@Autowired
	EntrepriseServiceImpl es;
	
	@Autowired
	EntrepriseRepository er;
	
	@Test
	public void testAddEntreprise() {
		try {
		Entreprise E = new Entreprise("Samsung","EURL");
		int Id = es.ajouterEntreprise(E);
		assertEquals(Id, E.getId());
		es.deleteEntrepriseById(Id);
		l.info("Add Entreprise works");
		} catch (NullPointerException e) {
			l.error(e.getMessage());
		}
	}
	
	@Test
	public void testUpdateEntreprise() {
		try {
		Entreprise E = new Entreprise("Samsung","EURL");
		int Id = es.ajouterEntreprise(E);
		E.setName("Iphone");
		es.ajouterEntreprise(E); 
		Entreprise Ese = es.getEntrepriseById(Id);
		assertEquals("Iphone",Ese.getName());
		es.deleteEntrepriseById(Id);
		l.info("Update Entreprise works");
		} catch (NullPointerException e) {
			l.error(e.getMessage());
		}
	}
	
	//test % size of list 
	@Test
	public void testDeleteEntrepriseById_METHOD1() {
		try {
		Entreprise E = new Entreprise("Samsung","EURL");
		int Id = es.ajouterEntreprise(E);
		int lengthBeforeDelete = es.getAllEntreprises().size();
		es.deleteEntrepriseById(Id);
		assertEquals(lengthBeforeDelete-1 , es.getAllEntreprises().size());
		l.info("Delete Entreprise (%size) works");
		} catch (NullPointerException e) {
			l.error(e.getMessage());
		}
	}
	
	//test % existence in DB
	@Test
	public void testDeleteEntrepriseById_METHOD2() {
		try {
			Entreprise E = new Entreprise("Samsung","EURL");
			int Id = es.ajouterEntreprise(E);
			boolean existsBeforeDelete = er.findById(Id).isPresent();
			es.deleteEntrepriseById(Id);
			boolean existsAfterDelete = er.findById(Id).isPresent();
			assertTrue(existsBeforeDelete);
			assertFalse(existsAfterDelete);
			l.info("Delete Entreprise (%existence) works");
			} catch (NullPointerException e) {
				l.error(e.getMessage());
			}
	}
	
	@Test
	public void testGetEntrepriseById(){
		try {
		Entreprise E = new Entreprise("Samsung","EURL");
		int Id = es.ajouterEntreprise(E);
		assertEquals(Id, E.getId());
		es.deleteEntrepriseById(Id);
		l.info("Get Entreprise by id works");
		} catch (NullPointerException e) {
			l.error(e.getMessage());
		}
	}
	
	@Test
	public void testAffectDepartmentToEntreprise(){
		try {
		Entreprise E = new Entreprise("Samsung","EURL");
		int IdE = es.ajouterEntreprise(E);
		Departement D = new Departement("Info");
		int IdD = es.ajouterDepartement(D);
		assertNull(D.getEntreprise());
		es.affecterDepartementAEntreprise(IdD, IdE);
		assertEquals(D.getEntreprise().getId(),IdE);
		es.deleteDepartementById(IdD);
		es.deleteEntrepriseById(IdE);
		l.info("Affect Department to Entreprise works");
		} catch (NullPointerException e) {
			l.error(e.getMessage());
		}
	}
	
	@Test
	public void testgetAllDepartementsNamesByEntreprise(){
		try {
		Entreprise E = new Entreprise("Samsung","EURL");
		int IdE = es.ajouterEntreprise(E);
		Departement D = new Departement("Info");
		int IdD = es.ajouterDepartement(D);
		Departement D2 = new Departement("RH");
		int IdD2 = es.ajouterDepartement(D2);
		es.affecterDepartementAEntreprise(IdD2, IdE);
		es.affecterDepartementAEntreprise(IdD, IdE);
		List<String> ExpectedNames = new ArrayList<>();
		ExpectedNames.add(D.getName());
		ExpectedNames.add(D2.getName());
		List<String> names = es.getAllDepartementsNamesByEntreprise(IdE);
		assertEquals(ExpectedNames,names);
		es.deleteDepartementById(IdD);
		es.deleteDepartementById(IdD2);
		es.deleteEntrepriseById(IdE);
		l.info("Get Names of Departments by Entreprise works");
		} catch (NullPointerException e) {
			l.error(e.getMessage());
		}
	}	  
}
