package br.univel.Trabalho1Bim;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
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

	public BancoPostGre() {

	}

	public void Inserir(String sql) {
		Conectar();
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Campo nao preenchido corretamente");
		}
		desconectar();

	}

	public void CriarTabela(String sqlTabela) {
		// Tenta Conectar
		try {
			Conectar();
			PreparedStatement cTable = con.prepareStatement(sqlTabela);
			cTable.executeUpdate();
		} catch (SQLException e2) {
			System.out.println("Tabela ja esta Criada");
		}
		desconectar();

	}

	public void DeletarTabela(String sqlTabela) {
		try {
			Conectar();
			PreparedStatement cTable = con.prepareStatement(sqlTabela);
			cTable.executeUpdate();
		} catch (SQLException e2) {
			System.out.println("Tabela ja existe");
		}
		desconectar();
	}

	public String geraTabela(Object o) {
		StringBuilder sb = new StringBuilder();
		Class<?> cb = o.getClass();
		String nomeTabela;

		if (cb.isAnnotationPresent(AnotaTabela.class)) {
			AnotaTabela at = cb.getAnnotation(AnotaTabela.class);
			if (at.nome().isEmpty()) {
				nomeTabela = cb.getSimpleName().toUpperCase();
			} else {
				nomeTabela = at.nome();
			}
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
			}
			if (tipoParametro.equals(int.class)) {
				tipoColuna = "INTEGER";
			} else if (tipoParametro.equals(String.class)) {
				tipoColuna = "VARCHAR(255)";
			} else if (tipoParametro.equals(long.class)) {
				tipoColuna = "BIGINT";
			} else if (tipoParametro.equals(double.class)) {
				tipoColuna = "DECIMAL";
			} else if (tipoParametro.equals(float.class)) {
				tipoColuna = "REAL";
			} else if (tipoParametro.equals(short.class)) {
				tipoColuna = "SMALLINT";
			} else if (tipoParametro.equals(BigDecimal.class)) {
				tipoColuna = "NUMERIC(12,2)";
			} else {
				tipoColuna = "DESCONHECIDO";
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
				if (at.nome().isEmpty()) {
					nomeTabela = cz.getSimpleName().toUpperCase();
				} else {
					nomeTabela = at.nome();
				}
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
			if (field.getType().getSimpleName().equals("String")) {
				sb.append("'").append(lista.get(i)).append("'");
			} else {
				sb.append(lista.get(i));
			}

		}
		sb.append(')');

		String strSql = sb.toString();
		return strSql;
	}

	public static void Conectar() {
		// deve trocar url
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

	public String getDeletarSql(Object o, String valore, String nomeColuna) {
		Class<?> cz = o.getClass();
		StringBuilder sb = new StringBuilder();

		{
			String nomeTabela;

			if (cz.isAnnotationPresent(AnotaTabela.class)) {
				AnotaTabela at = cz.getAnnotation(AnotaTabela.class);
				if (at.nome().isEmpty()) {
					nomeTabela = cz.getSimpleName().toUpperCase();
				} else {
					nomeTabela = at.nome();
				}
			} else {
				nomeTabela = cz.getSimpleName().toUpperCase();
			}
			sb.append("DELETE FROM ").append(nomeTabela).append(" WHERE ");
		}

		sb.append(nomeColuna).append(" = ").append(valore);

		String strSql = sb.toString();
		return strSql;
	}

	public String getAtualizaSql(Object o, String id, String nColuna, List<String> valores) {
		Class<? extends Object> cz = o.getClass();
		StringBuilder sb = new StringBuilder();
		String nomeColuna = null;
		{
			String nomeTabela;

			if (cz.isAnnotationPresent(AnotaTabela.class)) {
				AnotaTabela at = cz.getAnnotation(AnotaTabela.class);
				if (at.nome().isEmpty()) {
					nomeTabela = cz.getSimpleName().toUpperCase();
				} else {
					nomeTabela = at.nome();
				}
			} else {
				nomeTabela = cz.getSimpleName().toUpperCase();
			}
			sb.append("UPDATE ").append(nomeTabela).append(" SET ");
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
				if (i > 0) {
					sb.append(", ");
				}

				if (field.getType().getSimpleName().equals("String")) {
					sb.append(nomeColuna).append(" = '").append(valores.get(i)).append("'");
				} else {
					sb.append(nomeColuna).append(" = ").append(valores.get(i));
				}

			}
		}

		sb.append(" WHERE ").append(nColuna).append(" = ").append(id);

		String strSql = sb.toString();
		return strSql;
	}

	public static List<?> buscarTodos(String sql, Object p1) {
		Conectar();
		List<Object> lista = new ArrayList<Object>();
		int i = 1;
		int campos = p1.getClass().getDeclaredFields().length;
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				while (i <= campos) {
					lista.add(rs.getObject(i));
					i++;
				}
				i = 1;
			}
		} catch (SQLException e) {
			System.out.println("Esta Vazio");
		}

		return lista;
	}

	public String geraSqlBusca(Object o) {
		Class<? extends Object> cz = o.getClass();
		StringBuilder sb = new StringBuilder();
		{
			String nomeTabela;
			if (cz.isAnnotationPresent(AnotaTabela.class)) {
				AnotaTabela at = cz.getAnnotation(AnotaTabela.class);
				if (at.nome().isEmpty()) {
					nomeTabela = cz.getSimpleName().toUpperCase();
				} else {
					nomeTabela = at.nome();
				}
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

	public String getSqlDropTable(Object o) {
		Class<?> cz = o.getClass();
		StringBuilder sb = new StringBuilder();
		{
			String nomeTabela;
			if (cz.isAnnotationPresent(AnotaTabela.class)) {
				AnotaTabela at = cz.getAnnotation(AnotaTabela.class);
				if (at.nome().isEmpty()) {
					nomeTabela = cz.getSimpleName().toUpperCase();
				} else {
					nomeTabela = at.nome();
				}
			} else {
				nomeTabela = cz.getSimpleName().toUpperCase();
			}
			sb.append("DROP TABLE ").append(nomeTabela);
		}
		String strSql = sb.toString();
		return strSql;
	}

}
