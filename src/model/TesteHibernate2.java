/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;


import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;


/**
 *
 * @author taniabasso
 */
public class TesteHibernate2 {
    public static void main(String[] args) {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        session.beginTransaction();
        
        Aluno al1 = new Aluno(1234, "Maria");
        Aluno al2 = new Aluno(4567,"Jose");
        Aluno al3 = new Aluno(8900, "Marcelo");
        
        Professor prof1 = new Professor (111,"Tania");
        
        Disciplina disc1 = new Disciplina (1,"LPM");
        Disciplina disc2 = new Disciplina (2, "TCC");
        
        ArrayList<Disciplina> listaDisc = new ArrayList<>();
        listaDisc.add(disc1);
        listaDisc.add(disc2);
        
        prof1.setListaDisc(listaDisc);
        
        session.save(al1);
        session.save(al2);
        session.save(al3);
        
        session.save (disc1);
        session.save(disc2);
        
        session.save (prof1);
       
        session.getTransaction().commit();
       
        //String hql = "from Aluno where nome like 'M%'";
        //String hql = "from Aluno where ra = 4567";
        String hql = "from Aluno where nome like 'M%' order by ra";
        Query query = session.createQuery(hql);
        ArrayList<Aluno> lista = (ArrayList) query.list();
        for (Aluno a: lista)
        {
            System.out.println("RA: " + a.getRa() + " Nome: " + a.getNome());
        }
        
        String hql2 = "from Professor where matricula = 111";
        Query query2 = session.createQuery(hql2);
        ArrayList<Professor> lista2 = (ArrayList) query2.list();
        for (Professor p: lista2)
        {
            System.out.println("Matricula: " + p.getMatricula() + " Nome: " + p.getNome());
            List<Disciplina> listaDiscProf= p.getListaDisc();
            for (Disciplina disc:listaDiscProf)
            {
                System.out.println("--- Disciplina: " + disc.getId() + "  " + disc.getNomeDisc());
            }
        }
        
        session.close();
        HibernateUtil.shutdown();        
    }
}
