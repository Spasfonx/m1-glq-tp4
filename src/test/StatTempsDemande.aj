package test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import outils.Demande;
import outils.Sens;

public aspect StatTempsDemande {

	private List<Long> lesStats;

	private HashMap<Demande, Long> hashDemande;

	public StatTempsDemande() {
		lesStats = new ArrayList<Long>();
		hashDemande=new HashMap<Demande, Long>();
	}

	

	pointcut allumerBoutonAppel() : call(void operative.IIUG.allumerBouton(Demande));

	pointcut eteindreBoutonAppel() : call(void operative.IIUG.eteindreBouton(Demande));

	pointcut exitAppel(): execution(void commande.IControleur.exit());

	after():allumerBoutonAppel(){
		Demande d = (Demande) thisJoinPoint.getArgs()[0];
		if((!hashDemande.containsKey(d)) && !d.sens().equals(Sens.INDEFINI) ){
			hashDemande.put(d, new Date().getTime());
		}
	}

	after():eteindreBoutonAppel(){
		Demande d = (Demande) thisJoinPoint.getArgs()[0];
		if(hashDemande.containsKey(d)){
			lesStats.add(new Date().getTime() - hashDemande.get(d));
			hashDemande.remove(d);
		}
	}

	before():exitAppel(){
	long tempsMax=0;
	long tempsMin= Long.MAX_VALUE;
	long tempsMoy=0;
	for(long l:lesStats){
		if(l>tempsMax){
			tempsMax=l;
		}
		if(l<tempsMin){
			tempsMin=l;
		}
		tempsMoy+=l;
	}
	tempsMoy= tempsMoy/lesStats.size();
	
	System.out.println("temps max:"+tempsMax/1000 + "secondes");
	System.out.println("temps min:"+tempsMin/1000 + "secondes");
	System.out.println("temps moyen:"+tempsMoy/1000 + "secondes");
	}

}
