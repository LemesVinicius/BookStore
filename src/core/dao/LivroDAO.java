 package core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import les.dominio.Autor;
import les.dominio.Categoria;
import les.dominio.Dimensoes;
import les.dominio.Edicao;
import les.dominio.Editora;
import les.dominio.EntidadeDominio;
import les.dominio.GrupoPreco;
import les.dominio.Livro;
import les.dominio.SubCategoria;


public class LivroDAO extends AbstractJdbcDAO{

	public LivroDAO(String table, String idTable) {
		super(table, idTable);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {



		openConnection();
		PreparedStatement pst=null;
		PreparedStatement pst2=null;
		PreparedStatement pst3=null;
		PreparedStatement pst4=null;
		PreparedStatement pst5=null;
		PreparedStatement pst6=null;
		
		Livro livro = (Livro) entidade;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO tedicao( numero, ano, paginas, altura, largura, peso, profundidade)");
		sql.append("Values(?,?,?,?,?,?,?)");

		pst = connection.prepareStatement(sql.toString(), 
			Statement.RETURN_GENERATED_KEYS);

		pst.setString(1, livro.getEdicao().getNumero());
		pst.setString(2, livro.getEdicao().getAno());
		pst.setString(3, livro.getEdicao().getPaginas());
		pst.setInt(4, livro.getEdicao().getDimensoes().getAltura());
		pst.setInt(5, livro.getEdicao().getDimensoes().getLargura());
		pst.setInt(6, livro.getEdicao().getDimensoes().getPeso());
		pst.setInt(7, livro.getEdicao().getDimensoes().getProfundidade());

		pst.executeUpdate();
		
		final ResultSet rs = pst.getGeneratedKeys();
		
		
		
		if (rs.next()) {
			
			
			final int lastId = rs.getInt("GENERATED_KEY");
			livro.getEdicao().setId(lastId);
		}


		

		if(livro.getAutor().getNome() != null){

			StringBuilder sql2 = new StringBuilder();

			sql2.append("INSERT INTO tautor(nome)");
			sql2.append("Values(?)");

			pst2 = connection.prepareStatement(sql2.toString(), 
				Statement.RETURN_GENERATED_KEYS);

			pst2.setString(1, livro.getAutor().getNome());

			pst2.executeUpdate();

			final ResultSet rs2 = pst2.getGeneratedKeys();


			if (rs2.next()) {
				final int lastId2 = rs2.getInt("GENERATED_KEY");
				livro.getAutor().setId(lastId2);
			}


		}

		if(livro.getEditora().getNome() != null){

			StringBuilder sql3 = new StringBuilder();

			sql3.append("INSERT INTO teditora(nome)");
			sql3.append("Values(?)");

			pst3 = connection.prepareStatement(sql3.toString(), 
				Statement.RETURN_GENERATED_KEYS);

			pst3.setString(1, livro.getEditora().getNome());

			pst3.executeUpdate();

			final ResultSet rs3 = pst3.getGeneratedKeys();


			if (rs3.next()) {
				final int lastId3 = rs3.getInt("GENERATED_KEY");
				livro.getEditora().setId(lastId3);
			}


		}


		StringBuilder sql4 = new StringBuilder();		
		
		sql4.append("INSERT INTO tlivro(titulo, autor, editora, edicao, ISBN, sinopse, grupo, situacao,capa)"); 
		sql4.append("values(?,?,?,?,?,?,?,?,?)");

		pst4 = connection.prepareStatement(sql4.toString(), 
			Statement.RETURN_GENERATED_KEYS);

		pst4.setString(1, livro.getNome());
		pst4.setInt(2, livro.getAutor().getId());
		pst4.setInt(3, livro.getEditora().getId());
		pst4.setInt(4, livro.getEdicao().getId());
		pst4.setString(5, livro.getISBN());
		pst4.setString(6, livro.getSinopse());
		pst4.setInt(7, livro.getGrupopreco().getId());
		pst4.setInt(8, 1);
		pst4.setString(9, livro.getCapa());

		pst4.executeUpdate();
		
		final ResultSet rs4 = pst4.getGeneratedKeys();
		
		
		if (rs4.next()) {
			final int lastId4 = rs4.getInt("GENERATED_KEY");
			livro.setId(lastId4);;
		}		
		
		StringBuilder sql5 = new StringBuilder();	
		
		for(int i=0; i < livro.getCategorias().size();i++){
			
			sql5.setLength(0);
			pst5=null;
			
			sql5.append("INSERT INTO tlivcat(idlivro, idcategoria)");
			sql5.append("values(?,?)");
			pst5 = connection.prepareStatement(sql5.toString(), 
				Statement.RETURN_GENERATED_KEYS);
			pst5.setInt(1, livro.getId());
			pst5.setInt(2, livro.getCategorias().get(i).getId());
			pst5.executeUpdate();
			
			
		}
		
		StringBuilder sql6 = new StringBuilder();
		
		
		for(int i=0; i < livro.getSubcategorias().size();i++){
			
			sql6.setLength(0);
			pst6=null;
			
			sql6.append("INSERT INTO tlivsubcat(idlivro, idsubcat)");
			sql6.append("values(?,?)");
			pst6 = connection.prepareStatement(sql6.toString(), 
				Statement.RETURN_GENERATED_KEYS);
			pst6.setInt(1, livro.getId());
			pst6.setInt(2, livro.getSubcategorias().get(i).getId());
			pst6.executeUpdate();
			
			
			
		}

		 
		connection.close();

	}

	
	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
		

		openConnection();
		PreparedStatement pst=null;
		PreparedStatement pst2=null;
		PreparedStatement pst3=null;
		PreparedStatement pst4=null;
		PreparedStatement pst5=null;
		PreparedStatement pst6=null;
		PreparedStatement pstdel=null;
		PreparedStatement pstdel2=null;
		
		Livro livro = (Livro) entidade;
		
		StringBuilder sql = new StringBuilder();
		
		connection.setAutoCommit(false);
		
		sql.append("UPDATE tedicao SET numero=?, ano=?, paginas=?, altura=?, largura=?, peso=?, profundidade=?");
		sql.append(" WHERE id=?");

		pst = connection.prepareStatement(sql.toString(), 
			Statement.RETURN_GENERATED_KEYS);
		

		pst.setString(1, livro.getEdicao().getNumero());
		pst.setString(2, livro.getEdicao().getAno());
		pst.setString(3, livro.getEdicao().getPaginas());
		pst.setInt(4, livro.getEdicao().getDimensoes().getAltura());
		pst.setInt(5, livro.getEdicao().getDimensoes().getLargura());
		pst.setInt(6, livro.getEdicao().getDimensoes().getPeso());
		pst.setInt(7, livro.getEdicao().getDimensoes().getProfundidade());
		pst.setInt(8, livro.getEdicao().getId());

		pst.executeUpdate();


		if(livro.getAutor().getNome() != null){

			StringBuilder sql2 = new StringBuilder();

			sql2.append("INSERT INTO tautor(nome)");
			sql2.append("Values(?)");

			pst2 = connection.prepareStatement(sql2.toString(), 
				Statement.RETURN_GENERATED_KEYS);

			pst2.setString(1, livro.getAutor().getNome());

			pst2.executeUpdate();

			final ResultSet rs2 = pst2.getGeneratedKeys();

			if (rs2.next()) {
				final int lastId2 = rs2.getInt("GENERATED_KEY");
				livro.getAutor().setId(lastId2);
			}

		}

		if(livro.getEditora().getNome() != null){

			StringBuilder sql3 = new StringBuilder();

			sql3.append("INSERT INTO teditora(nome)");
			sql3.append("Values(?)");

			pst3 = connection.prepareStatement(sql3.toString(), 
				Statement.RETURN_GENERATED_KEYS);

			pst3.setString(1, livro.getEditora().getNome());

			pst3.executeUpdate();

			final ResultSet rs3 = pst3.getGeneratedKeys();


			if (rs3.next()) {
				final int lastId3 = rs3.getInt("GENERATED_KEY");
				livro.getEditora().setId(lastId3);
			}


		}


		StringBuilder sql4 = new StringBuilder();		
		
		sql4.append("UPDATE tlivro SET titulo=?, autor=?, editora=?, ISBN=?, sinopse=?, grupo=? "); 
		sql4.append(" WHERE id=?");

		pst4 = connection.prepareStatement(sql4.toString(), 
			Statement.RETURN_GENERATED_KEYS);

		pst4.setString(1, livro.getNome());
		pst4.setInt(2, livro.getAutor().getId());
		pst4.setInt(3, livro.getEditora().getId());
		pst4.setString(4, livro.getISBN());
		pst4.setString(5, livro.getSinopse());
		pst4.setInt(6, livro.getGrupopreco().getId());
		pst4.setInt(7, livro.getId());
		

		pst4.executeUpdate();
		

		StringBuilder delete = new StringBuilder();
		StringBuilder delete2 = new StringBuilder();


		delete.append("DELETE FROM tlivcat WHERE idlivro = ?");
		pstdel = connection.prepareStatement(delete.toString(), 
			Statement.RETURN_GENERATED_KEYS);
		pstdel.setInt(1, livro.getId());
		pstdel.executeUpdate();

		delete2.append("DELETE FROM tlivsubcat WHERE idlivro = ?");
		pstdel2 = connection.prepareStatement(delete2.toString(), 
			Statement.RETURN_GENERATED_KEYS);
		pstdel2.setInt(1, livro.getId());
		pstdel2.executeUpdate();


		
		StringBuilder sql5 = new StringBuilder();		
		
		for(int i=0; i < livro.getCategorias().size();i++){
			
			sql5.setLength(0);
			pst5=null;
			
			sql5.append("INSERT INTO tlivcat(idlivro, idcategoria)");
			sql5.append("values(?,?)");
			pst5 = connection.prepareStatement(sql5.toString(), 
				Statement.RETURN_GENERATED_KEYS);
			pst5.setInt(1, livro.getId());
			pst5.setInt(2, livro.getCategorias().get(i).getId());
			pst5.executeUpdate();
			
			
		}
		
		StringBuilder sql6 = new StringBuilder();
		
		
		for(int i=0; i < livro.getSubcategorias().size();i++){
			
			sql6.setLength(0);
			pst6=null;
			
			sql6.append("INSERT INTO tlivsubcat(idlivro, idsubcat)");
			sql6.append("values(?,?)");
			pst6 = connection.prepareStatement(sql6.toString(), 
				Statement.RETURN_GENERATED_KEYS);
			pst6.setInt(1, livro.getId());
			pst6.setInt(2, livro.getSubcategorias().get(i).getId());
			pst6.executeUpdate();
			
			
			
		}
		
		
		
		
		 connection.commit();
		 connection.close();
		
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
	
		openConnection();
		PreparedStatement pst=null;
		PreparedStatement pst2=null;
		PreparedStatement pst3=null;
		
		Livro livro = (Livro) entidade;
		
		
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		
		if(livro.getId() == null && livro.getNome() == null){//Todos os campos empty(Select *)
			
			sql.append("SELECT tlivro.id, tlivro.situacao, tlivro.titulo, tlivro.ISBN, tlivro.sinopse, tautor.id AS autor, tautor.nome AS autornome, teditora.id AS editora, teditora.nome AS editoranome, tedicao.id, tedicao.numero, tedicao.ano, tedicao.paginas, tedicao.altura, tedicao.largura, tedicao.profundidade, tedicao.peso, tgrupop.id, tgrupop.nome, tgrupop.valor, tlivro.capa");
			sql.append(" FROM ((((tlivro ");	
			sql.append(" INNER JOIN tautor ON  tlivro.autor = tautor.id)");
			sql.append(" INNER JOIN teditora ON tlivro.editora = teditora.id)");
			sql.append(" INNER JOIN tedicao ON tlivro.edicao = tedicao.id)");
			sql.append(" INNER JOIN tgrupop ON tlivro.grupo = tgrupop.id)");
			
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);			
			
		}
		else if(livro.getId() != null && livro.getNome() == null){//todos menos ID null (Busca por ID)
			
			sql.append("SELECT tlivro.id, tlivro.situacao, tlivro.titulo, tlivro.ISBN, tlivro.sinopse, tautor.id AS autor, tautor.nome AS autornome, teditora.id AS editora, teditora.nome AS editoranome, tedicao.id,tedicao.numero, tedicao.ano, tedicao.paginas, tedicao.altura, tedicao.largura, tedicao.profundidade, tedicao.peso, tgrupop.id, tgrupop.nome, tgrupop.valor, tlivro.capa");
			sql.append(" FROM ((((tlivro ");	
			sql.append(" INNER JOIN tautor ON  tlivro.autor = tautor.id)");
			sql.append(" INNER JOIN teditora ON tlivro.editora = teditora.id)");
			sql.append(" INNER JOIN tedicao ON tlivro.edicao = tedicao.id)");
			sql.append(" INNER JOIN tgrupop ON tlivro.grupo = tgrupop.id)");
			sql.append(" WHERE tlivro.id=?");
			
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);			
			
			
			pst.setInt(1, livro.getId());
			
		}
		else{//Busca combinada
		
		sql.append("SELECT tlivro.id, tlivro.situacao, tlivro.titulo, tlivro.ISBN, tlivro.sinopse, tautor.id AS autor, tautor.nome AS autornome, teditora.id AS editora, teditora.nome AS editoranome, tedicao.id,tedicao.numero, tedicao.ano, tedicao.paginas, tedicao.altura, tedicao.largura, tedicao.profundidade, tedicao.peso, tgrupop.id, tgrupop.nome, tgrupop.valor, tlivro.capa");
		sql.append(" FROM ((((tlivro ");	
		sql.append(" INNER JOIN tautor ON  tlivro.autor = tautor.id)");
		sql.append(" INNER JOIN teditora ON tlivro.editora = teditora.id)");
		sql.append(" INNER JOIN tedicao ON tlivro.edicao = tedicao.id)");
		sql.append(" INNER JOIN tgrupop ON tlivro.grupo = tgrupop.id)");
		sql.append(" WHERE titulo=? AND autor=? AND ano=? AND editora=? AND edicao=? AND ISBN=? AND paginas=? AND sinopse=? AND grupo=? OR tlivro.id=?");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);			
		
		
		pst.setString(1, livro.getNome());
		pst.setString(3, livro.getEdicao().getAno());
		pst.setString(5, livro.getEdicao().getNumero());
		pst.setString(6, livro.getISBN());
		pst.setString(7, livro.getEdicao().getPaginas());
		pst.setString(8, livro.getSinopse());
		
		if(livro.getAutor().getId() != null ){
			pst.setInt(2,livro.getAutor().getId());
		}else{
			pst.setNull(2, java.sql.Types.INTEGER);	
		}
		
		if(livro.getEditora().getId() != null){
			pst.setInt(4, livro.getEditora().getId());
		}else{
			pst.setNull(4, java.sql.Types.INTEGER);	
		}
		
		if(livro.getGrupopreco().getId() != null){
			pst.setInt(9, livro.getGrupopreco().getId());
		}else{
			pst.setNull(9, java.sql.Types.INTEGER);
		}
		
		
		if(livro.getId() == null){
			livro.setId(0);
		}			
		pst.setInt(10, livro.getId());
		
		}
		
		
		
		ResultSet rs = pst.executeQuery();
		List<EntidadeDominio> livros = new ArrayList<EntidadeDominio>();
		
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		
		
		if(livro.getId() != null){
			
			sql.setLength(0);
			
			sql.append("SELECT * FROM tlivcat WHERE idlivro=?" );
			pst2 = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
			pst2.setInt(1, livro.getId());
			rs2 = pst2.executeQuery();
			
			sql.setLength(0);
			sql.append("SELECT * FROM tlivsubcat WHERE idlivro=?" );
			pst3 = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
			pst3.setInt(1, livro.getId());
			 rs3 = pst3.executeQuery();
			
		}
		
		
		
		while (rs.next()) {
		
			Livro l = new Livro();
			Autor a = new Autor();
			Edicao e = new Edicao();
			Editora ed = new Editora();
			GrupoPreco grp = new GrupoPreco();
			Dimensoes dm = new Dimensoes();
			List<Categoria> cats = new ArrayList<Categoria>();
			List<SubCategoria> subcats = new ArrayList<SubCategoria>();
			
			e.setDimensoes(dm);
			l.setAutor(a);
			l.setEdicao(e);
			l.setEditora(ed);
			l.setGrupopreco(grp);
			l.setCategorias(cats);
			l.setSubcategorias(subcats);
			
			l.setId(rs.getInt(1));
			l.setNome(rs.getString(3));
			l.setISBN(rs.getString(4));
			l.setSinopse(rs.getString(5));
			l.getAutor().setId(rs.getInt(6));
			l.getAutor().setNome(rs.getString(7));
			l.getEditora().setId(rs.getInt(8));
			l.getEditora().setNome(rs.getString(9));
			l.getEdicao().setId(rs.getInt(10));
			l.getEdicao().setNumero(rs.getString(11));
			l.getEdicao().setAno(rs.getString(12));
			l.getEdicao().setPaginas(rs.getString(13));
			l.getEdicao().getDimensoes().setAltura(rs.getInt(14));
			l.getEdicao().getDimensoes().setLargura(rs.getInt(15));
			l.getEdicao().getDimensoes().setProfundidade(rs.getInt(16));
			l.getEdicao().getDimensoes().setPeso(rs.getInt(17));
			l.getGrupopreco().setId(rs.getInt(18));
			l.getGrupopreco().setNome(rs.getString(19));
			l.getGrupopreco().setMargemLucro(rs.getInt(20));
			l.setCapa(rs.getString(21));
			
			
			int situ = (rs.getInt(2));
			
			if(situ == 0)
				l.setAtivo(false);
			else
				l.setAtivo(true);
			
			
			
			List<Categoria> categorias = new ArrayList<Categoria>();
			List<SubCategoria> scategorias = new ArrayList<SubCategoria>();
						
			
			if(livro.getId() != null){
				while(rs2.next()){
					
					Categoria c = new Categoria();
					CategoryDAO catdao = new CategoryDAO("","");
					
					c.setId(rs2.getInt("idcategoria"));
					c = (Categoria)catdao.consultar(c).get(0);
					categorias.add(c);
					
				}
				
				while(rs3.next()){
					
					SubCategoria sc = new SubCategoria();
					SubCategoriaDAO scatdao = new SubCategoriaDAO("","");
					
					sc.setId(rs3.getInt("idsubcat"));
					sc = (SubCategoria)scatdao.consultar(sc).get(0);
					scategorias.add(sc);
					
				}
			}
					
			l.setCategorias(categorias);
			l.setSubcategorias(scategorias);
			
			livros.add(l);
		
		}
		
		
		
		 connection.close();
		
		return livros;
	}

	public void ativar(EntidadeDominio entidade) throws SQLException{
		

		openConnection();
		PreparedStatement pst=null;
		connection.setAutoCommit(false); 
		
		
		Livro livro = (Livro) entidade;
		
		StringBuilder sql = new StringBuilder();
		
		
		sql.append("UPDATE tlivro SET situacao = '1'");
		sql.append(" WHERE id = ? ");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		pst.setInt(1, livro.getId());
		pst.executeUpdate();
		
		pst.close();
		connection.commit();
		connection.close();
		
		
	}
	
	public void desativar(EntidadeDominio entidade) throws SQLException{
		 openConnection();
		PreparedStatement pst=null;
		connection.setAutoCommit(false); 
		
		
		Livro livro = (Livro) entidade;
		
		StringBuilder sql = new StringBuilder();
		
		
		sql.append("UPDATE tlivro SET situacao=0");
		sql.append(" WHERE id=?");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		pst.setInt(1, livro.getId());
		pst.executeUpdate();

		pst.close();
		connection.commit();
		connection.close();
		
		
		
		
	}
	
}
