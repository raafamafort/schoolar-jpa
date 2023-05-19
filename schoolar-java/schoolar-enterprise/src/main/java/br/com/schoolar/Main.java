package br.com.schoolar;

import br.com.schoolar.aluno.model.Aluno;
import br.com.schoolar.classroom.model.Classroom;
import br.com.schoolar.materia.model.Materia;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("oracle");
        EntityManager entityManager = factory.createEntityManager();

        Materia materia = new Materia();
        materia.setName("Portugues");
        materia.setAverage(new BigDecimal(9));
        materia.setAbsences(2);

        Classroom classroom = new Classroom();
        classroom.setClassNumber("77");
        classroom.setWeeklyLessons(6);

        Aluno aluno = new Aluno();
        aluno.setName("Lucas");
        aluno.setRm("99778");
        aluno.setEmail("rm99778@fiap.com.br");
        aluno.setPassword("0000");
        aluno.setBirthDate(LocalDate.of(2004,11,10));
        aluno.setClassroom(classroom);
        aluno.addMateria(materia);

        Aluno aluno2 = new Aluno();
        aluno2.setName("Rafael");
        aluno2.setRm("95699");
        aluno2.setEmail("rm95699@fiap.com.br");
        aluno2.setPassword("12345");
        aluno2.setBirthDate(LocalDate.of(2003,9,22));
        aluno2.setClassroom(classroom);
        aluno2.addMateria(materia);


        entityManager.getTransaction().begin();

        entityManager.persist(materia);
        entityManager.persist(classroom);
        entityManager.persist(aluno);
        entityManager.persist(aluno2);

        entityManager.getTransaction().commit();


        Aluno alunoConsultado  = consultarAlunoPorId(entityManager, "1");
        System.out.println("Aluno consultado pelo id: " + alunoConsultado);

        List<Aluno> alunos = consultarAlunosPorClassroomId(entityManager, "1");
        System.out.println("Alunos da sala de id 1:");
        for (Aluno aluno_consulta : alunos) {
            System.out.println(aluno_consulta.getName() + ", RM" + aluno_consulta.getRm());
        }

        Aluno alterarAluno = atualizarAlunoPorRm(entityManager, "95699");
        System.out.println("Nome atualizado " + alterarAluno);

        deletarAlunoPorId(entityManager,"1");

        List<Aluno> todosAlunos = consultarTodosAlunos(entityManager);
        System.out.println("Consultar todos os Alunos: " + todosAlunos);

        entityManager.close();
    }

    public static Aluno consultarAlunoPorId(EntityManager entityManager, String id) {
        return entityManager.find(Aluno.class, id);
    }

    public static List<Aluno> consultarAlunosPorClassroomId(EntityManager entityManager, String id) {
        TypedQuery<Aluno> query = entityManager.createQuery("SELECT a FROM Aluno a WHERE a.classroom.id = :classroomId", Aluno.class);
        query.setParameter("classroomId", id);
        return query.getResultList();
    }

    public static Aluno atualizarAlunoPorRm(EntityManager entityManager, String rm) {
        TypedQuery<Aluno> query = entityManager.createQuery(
                "SELECT a FROM Aluno a WHERE a.rm = :rm",
                Aluno.class
        );
        query.setParameter("rm", rm);
        Aluno aluno = query.getSingleResult();

        if (aluno != null) {
            aluno.setName("Rafael Mafort");
        }
        return aluno;
    }

    public static void deletarAlunoPorId(EntityManager entityManager, String id) {
        Aluno aluno = entityManager.find(Aluno.class, id);
        if (aluno != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(aluno);
            entityManager.getTransaction().commit();
            System.out.println("Aluno deletado: " + aluno.getName());
        } else {
            System.out.println("Aluno não encontrado para o id: " + id);
        }
    }


    public static List<Aluno> consultarTodosAlunos(EntityManager entityManager) {
        String jpql = "SELECT a FROM Aluno a";
        TypedQuery<Aluno> query = entityManager.createQuery(jpql, Aluno.class);
        return query.getResultList();
    }
}
