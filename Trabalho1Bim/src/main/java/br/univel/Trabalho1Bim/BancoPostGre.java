package br.univel.Trabalho1Bim;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.univel.anotacoes.AnotaColuna;
import br.univel.anotacoes.AnotaTabela;

public class BancoPostGre {

	private static Connection con;
	private static boolean cTable = true;

	public BancoPostGre() {
		String sqlTabela = geraTabela(Pessoa.class);
		//con = new Conexao();
		PreparedStatement ps = null;
		//Cria tabela
		if(cTable){
			CriarTabela(sqlTabela);
			cTable = false;
		}
		
		
	}
	
	public void Inserir(String sql) {
		Conectar();
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Campo nao preenchido");
		}
		desconectar();
		
	}

	


	private void CriarTabela(String sqlTabela) {
		//Tenta Conectar
		try {
			Conectar();
			PreparedStatement cTable = con.prepareStatement(sqlTabela);
			cTable.executeUpdate();
		} catch (SQLException e2) {
			System.out.println("Tabela ja existe");
		}
		desconectar();
	
	}



	private String geraTabela(Class<Pessoa> cb) {
		StringBuilder sb = new StringBuilder();
		String nomeTabela;

		if (cb.isAnnotationPresent(AnotaTabela.class)) {
			AnotaTabela at = cb.getAnnotation(AnotaTabela.class);
			nomeTabela = at.nome();
		} else {
			nomeTabela = cb.getSimpleName().toUpperCase();
		}

		sb.append("CREATE TABLE ").append(nomeTabela).append(" (");

		Field[] atributos = cb.getDeclaredFields();

		for (int i = 0; i < atributos.length; i++) {
			
			Field field = atributos[i];

			String nomeColuna = null;
			String tipoColuna = null;
			Class<?> tipoParametro = field.getType();
			AnotaColuna ac = field.getAnnotation(AnotaColuna.class);
			field.setAccessible(true);

			if (field.isAnnotationPresent(AnotaColuna.class)) {
				if (ac.nome().isEmpty()) {
					nomeColuna = field.getName().toUpperCase();
				} else {
					nomeColuna = ac.nome();
				}
				if (!ac.tipo().isEmpty()) {
					tipoColuna = ac.tipo();
				}
			} else {
				nomeColuna = field.getName().toUpperCase();
				if (tipoParametro.equals(int.class)) {
					tipoColuna = "INTEGER";
				} else if (tipoParametro.equals(String.class)) {
					tipoColuna = "VARCHAR(255)";
				} else if (tipoParametro.equals(Float.class) || tipoParametro.equals(Double.class)) {
					tipoColuna = "DECIMAL";
				} else {
					tipoColuna = "DESCONHECIDO";
				}
			}

			if (i > 0) {
				sb.append(",");
			}

			sb.append("\n\t").append(nomeColuna).append(" ").append(tipoColuna);
		}
		sb.append("\n);");

		return sb.toString();
	}

	public String getInserirSql(Object p1, List<String> lista) {
		
		Class<? extends Object> cz = p1.getClass();
		StringBuilder sb = new StringBuilder();

		{
			String nomeTabela;

			if (cz.isAnnotationPresent(AnotaTabela.class)) {
				AnotaTabela at = cz.getAnnotation(AnotaTabela.class);
				nomeTabela = at.nome();
			} else {
				nomeTabela = cz.getSimpleName().toUpperCase();
			}
			sb.append("INSERT INTO ").append(nomeTabela).append(" (");
		}

		Field[] atributos = cz.getDeclaredFields();
		{
			for (int i = 0; i < atributos.length; i++) {
				Field field = atributos[i];
				String nomeColuna;

				if (field.isAnnotationPresent(AnotaColuna.class)) {
					AnotaColuna ac = field.getAnnotation(AnotaColuna.class);

					if (ac.nome().isEmpty()) {
						nomeColuna = field.getName().toUpperCase();
					} else {
						nomeColuna = ac.nome();
					}
				} else {
					nomeColuna = field.getName().toUpperCase();
				}
				if (i > 0) {
					sb.append(", ");
				}
				
				sb.append(nomeColuna);
				
			}
		}
		sb.append(") ").append("VALUES (");
		for (int i = 0; i < atributos.length; i++) {
			Field field = atributos[i];
			if (i > 0) {
				sb.append(", ");
			}
			if(field.getType().getSimpleName().equals("String")){
				sb.append("'").append(lista.get(i)).append("'");
			}else{
				sb.append(lista.get(i));
			}
			
		}
		sb.append(')');

		String strSql = sb.toString();
		return strSql;
	}

	public static void Conectar() {
		String url = "jdbc:postgresql://localhost:5432/TrabalhoJava", user = "postgres", pass = "123456";
		try {
			con = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void desconectar() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}


	

	public String getDeletarSql(Object o, String valore,String nomeColuna) {
		Class<? extends Object> cz = o.getClass();
		StringBuilder sb = new StringBuilder();

		{
			String nomeTabela;

			if (cz.isAnnotationPresent(AnotaTabela.class)) {
				AnotaTabela at = cz.getAnnotation(AnotaTabela.class);
				nomeTabela = at.nome();
			} else {
				nomeTabela = cz.getSimpleName().toUpperCase();
			}
			sb.append("DELETE FROM ").append(nomeTabela).append(" WHERE ");
		}

		sb.append(nomeColuna).append(" = ").append(valore);

		String strSql = sb.toString();
		return strSql;
	}
	
	

	public String getAtualizaSql(Object o, String id,String nColuna, List<String> valores) {
		Class<? extends Object> cz = o.getClass();
		StringBuilder sb = new StringBuilder();
		String nomeColuna = null;
		{
			String nomeTabela;

			if (cz.isAnnotationPresent(AnotaTabela.class)) {
				AnotaTabela at = cz.getAnnotation(AnotaTabela.class);
				nomeTabela = at.nome();
			} else {
				nomeTabela = cz.getSimpleName().toUpperCase();
			}
			sb.append("UPDATE ").append(nomeTabela).append(" SET ");
			//UPDATE COMPANY SET SALARY = 15000 WHERE ID = 3; .append(" WHERE ")
		}
		
		
		Field[] atributos = cz.getDeclaredFields();
		{
			for (int i = 0; i < atributos.length; i++) {
				Field field = atributos[i];
				
				if (field.isAnnotationPresent(AnotaColuna.class)) {
					AnotaColuna ac = field.getAnnotation(AnotaColuna.class);

					if (ac.nome().isEmpty()) {
						nomeColuna = field.getName().toUpperCase();
					} else {
						nomeColuna = ac.nome();
					}
				} else {
					nomeColuna = field.getName().toUpperCase();
				}
				if(i>1){
					sb.append(", ");
				}
				if (i > 0) {
					if(field.getType().getSimpleName().equals("String")){
						sb.append(nomeColuna).append(" = '").append(valores.get(i)).append("'");
					}else{
						sb.append(nomeColuna).append(" = ").append(valores.get(i));
					}
				}
				
				
				
				
			}
		}
		
		sb.append(" WHERE ").append(nColuna).append(" = ").append(id);

		String strSql = sb.toString();
		return strSql;
	}

	public static List<?> buscarTodos(String sql,Object p1) {
		Conectar();
		List<Object> lista = new ArrayList<Object>();
		int i = 1;
		int campos = p1.getClass().getDeclaredFields().length;
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				while (i  <= campos) {
					lista.add(rs.getObject(i));
					i++;
				}
				i=1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lista;
	}
	/*public static List<Aluno> buscarTodos() {
		List<Aluno> lista = new ArrayList<>();
		String sql = "select * from  aluno;";
		PreparedStatement ps;

		try {
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Aluno a = new Aluno();
				a.setId(rs.getInt(1));
				a.setNome(rs.getString(2));
				lista.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;

	}

	 * */

	public String geraSqlBusca(Object o) {
		Class<? extends Object> cz = o.getClass();
		StringBuilder sb = new StringBuilder();
		{
			String nomeTabela;
			if (cz.isAnnotationPresent(AnotaTabela.class)) {
				AnotaTabela at = cz.getAnnotation(AnotaTabela.class);
				nomeTabela = at.nome();
			} else {
				nomeTabela = cz.getSimpleName().toUpperCase();
			}
			sb.append("SELECT *FROM ").append(nomeTabela);
		}
		String strSql = sb.toString();
		return strSql;
	}
	
	public static void main(String[] args) {

		new BancoPostGre();
	}
}