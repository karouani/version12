package com.mql.strut.web.actions;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.mql.strut.web.models.CollaborateurModel;
import com.mql.strut.web.models.Manager;
import com.opensymphony.xwork2.ModelDriven;
import com.sqli.challange.entity.Collaborateur;
import com.sqli.challange.sessions.IManagerRemote;

@Stateless
public class AjouterCollaborateurAction implements ModelDriven<CollaborateurModel>{
	
	private String messageCollaborateur;
	private String menu;
	
	private CollaborateurModel colab;
	
	private Collaborateur col;

	private Long bu;
	private Long site;
	private List<String> techno;
	private List<String> diplome;
	private Long manageractuel;
	
	@EJB
	private IManagerRemote manager;
	
	public AjouterCollaborateurAction() {
		colab = new CollaborateurModel();
	}
	//Injection
	public void setMenu(String type) {
		this.menu = type;
	}
	
	public void setMessageCollaborateur(String messageCollaborateur) {
		this.messageCollaborateur = messageCollaborateur;
	}
	public void setCol(Collaborateur col) {
		this.col = col;
	}
	public void setBu(Long bu) {
		this.bu = bu;
	}
	public void setSite(Long site) {
		this.site = site;
	}
	public void setManageractuel(Long manageractuel) {
		this.manageractuel = manageractuel;
	}
	public void setTechno(List<String> techno) {
		this.techno = techno;
	}
	public void setDiplome(List<String> diplome) {
		this.diplome = diplome;
	}
	
	//Methode d'injection des listes
	public void remplirTechno() {
		techno = new ArrayList<>();
		for (int i = 0; i < colab.getLevel().size(); i++) {
			techno.add(colab.getDesctechno().get(i)+","+colab.getComp().get(i)+","+colab.getLevel().get(i));
		}
	}

	public void remplirDiplome() {
		diplome = new ArrayList<>();
		for (int i = 0; i < colab.getEcole().size(); i++) {
			diplome.add(colab.getEcole().get(i)+","+
					colab.getPromotion().get(i)+","+
					colab.getEcole().get(i)+","+
					colab.getType_diplome().get(i)+","+
					colab.getType_ecole().get(i)+","+
					colab.getNiveau().get(i));

		}
	}

	//Methode d'action
	public String execute(){
		this.remplirTechno();
		this.remplirDiplome();
		col = new Collaborateur(colab.getMatricule(), 
								colab.getNom(), 
								colab.getPrenom(), 
								colab.getAbreviation(), 
								colab.getSexe(), 
								colab.getDateembauche(), 
								colab.getMoisBAP(), 
								colab.getParticipeseminaire(), 
								colab.getDateparticipeseminaire(), 
								colab.getSalaireactuel(), 
								colab.getPosttravail(), 
								colab.getEmail());
		
		manager.ajouterCollaborateur(col, techno, diplome, bu, site, manageractuel);
		messageCollaborateur = "Collaborateur ajoutŽ avec success !!";
		menu = "nouveauCollaborateur";
		return "success";
	}
	
	public CollaborateurModel getModel() {
		return colab;
	}
	public String getMessageCollaborateur() {
		return messageCollaborateur;
	}
	public Collaborateur getCol() {
		return col;
	}
	public Long getBu() {
		return bu;
	}
	public Long getSite() {
		return site;
	}
	public Long getManageractuel() {
		return manageractuel;
	}
	public List<String> getTechno() {
		return techno;
	}
	public List<String> getDiplome() {
		return diplome;
	}

	public String getMenu() {
		return menu;
	}

}
