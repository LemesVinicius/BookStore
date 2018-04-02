package core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import les.dominio.EntidadeDominio;
import les.dominio.Item;
import les.dominio.Livro;

public class ItemDAO extends AbstractJdbcDAO{

	public ItemDAO(String table, String idTable) {
		super(table, idTable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alterar(EntidadeDominio entidade) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	
	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
		
		Item i = (Item)entidade;
		
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		
		if(i.getId() != null){
			
			sql.append("SELECT titem.id, titem.valor, titem.produto, tlivro.id, tlivro.titulo, tlivro.autor, tlivro.editora, tlivro.ISBN, tlivro.sinopse "
					+ "FROM (titem INNER JOIN tlivro ON titem.produto = tlivro.id)");
			sql.append(" WHERE titem.id=?");
			
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
			
			pst.setInt(1, i.getId());
			
		}
		//----------------------------------------------------------------------------------
		if(i.getId() == null && i.getProduto() == null){
			
			sql.append("SELECT * FROM titem");
			
			pst = connection.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
		}
		
		//-----------------------------------------------------------------------------
		if(i.getProduto() != null) {
			
				sql.append("SELECT titem.id, titem.valor, titem.produto, tlivro.id, tlivro.titulo, tlivro.autor, tlivro.editora, tlivro.ISBN, tlivro.sinopse "
						+ "FROM (titem INNER JOIN tlivro ON titem.produto = tlivro.id) WHERE ");
				Livro l = (Livro)i.getProduto();
				
				Boolean primeiro = false;
				
				
				if(l.getNome() != null){
					sql.append("tlivro.titulo LIKE '%"+l.getNome()+"%'");
					primeiro = true;
					
				}if(l.getAutor() != null ){
					
					if(primeiro == true){
						sql.append(" AND ");
					}else{
						primeiro = true;
					}
					sql.append("tlivro.autor="+l.getAutor().getId());
					
					
				}if(l.getEditora() != null){
					
					if(primeiro == true){
						sql.append(" AND ");
					}else{
						primeiro = true;
					}
					
					sql.append("tlivro.editora="+l.getEditora().getId());
					
				}if(l.getISBN() != null){
					
					if(primeiro == true){
						sql.append(" AND ");
					}else{
						primeiro = true;
					}
					
					sql.append("tlivro.ISBN="+l.getISBN());
					
				}if(l.getSinopse() != null){
					
					if(primeiro == true){
						sql.append(" AND ");
					}else{
						primeiro = true;
					}
					
					sql.append("tlivro.sinopse="+l.getSinopse());
					
				}
				
			
				pst = connection.prepareStatement(sql.toString(), 
						Statement.RETURN_GENERATED_KEYS);
							
			}
			
		
		
		//-----------------------------------------------------------------------------
		
		
		ResultSet rs = pst.executeQuery();
		
		List<EntidadeDominio> itens = new ArrayList<>();
		
		while(rs.next()){
			
			Item item = new Item();
			
			
			item.setId(rs.getInt(1));
			item.setValor(rs.getDouble(2));
			
			
			Livro l = new Livro();
			l.setId(rs.getInt(3));
			
			LivroDAO ldao = new LivroDAO(null, null); 
			item.setProduto((Livro)ldao.consultar(l).get(0));
			
			itens.add(item);
			
						
		}
				
		pst.close();
		connection.commit();
		connection.close();
		
		return itens;
		
	}

}
