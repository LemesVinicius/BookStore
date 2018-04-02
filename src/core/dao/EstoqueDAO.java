package core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import les.dominio.EntidadeDominio;
import les.dominio.Estoque;

public class EstoqueDAO extends AbstractJdbcDAO{

	public EstoqueDAO(String table, String idTable) {
		super(table, idTable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		
		Estoque est = (Estoque)entidade;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		
		sql.append("INSERT INTO testoque(Livro, estoque)");
		sql.append("values(?,?)");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		pst.setInt(1, est.getLivro());
		pst.setInt(2, est.getEstoque());
		
		pst.executeUpdate();
		
		pst.close();
		connection.commit();
		connection.close();
		
		
		
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		
		Estoque est = (Estoque) entidade;
		
		StringBuilder sql = new StringBuilder();
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false);
		
		sql.append("SELECT testoque.livro,testoque.quantidade, coalesce(SUM(treserva.quantidade), 0)"); 
		sql.append("FROM(testoque LEFT JOIN treserva ON testoque.livro = treserva.produto)"); 
		if(est.getLivro() != null){
		sql.append(" Where testoque.livro ="+est.getLivro());
		}
		sql.append(" GROUP BY testoque.livro");	
		
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
				
		
		List<EntidadeDominio> estoques = new ArrayList<EntidadeDominio>();
		
		ResultSet rs = pst.executeQuery();
		
		while(rs.next()){
		
			Estoque e = new Estoque();
			
			e.setLivro(rs.getInt(1));
			e.setEstoque(rs.getInt(2));
			e.setReservado(rs.getInt(3));
			
			estoques.add(e);
		
		}
		
		
		pst.close();
		connection.commit();
		connection.close();
		
		return estoques;
		
	}

}
