package core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import les.dominio.Cidade;
import les.dominio.Endereco;
import les.dominio.EntidadeDominio;
import les.dominio.Estado;
import les.dominio.Pais;
import les.dominio.TipoEndereco;
import les.dominio.TipoLogradouro;


public class EnderecoDAO extends AbstractJdbcDAO {

	public EnderecoDAO(String table, String idTable) {
		super(table, idTable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void salvar(EntidadeDominio entidade) throws SQLException {
		
		
		openConnection();
		PreparedStatement pst=null;
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		Endereco end =(Endereco)entidade;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO tend( cliente, alias, tipoend, tipologradouro, logradouro, numero, bairro, cep, cidade, estado, pais , obs) ");
		sql.append("Values(?,?,?,?,?,?,?,?,?,?,?,?)");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		pst.setInt(1, end.getCliente());
		pst.setString(2, end.getAlias());
		pst.setInt(3, end.getTipoend().getId());
		pst.setInt(4, end.getTipologra().getId());
		pst.setString(5, end.getLogradouro());
		pst.setString(6, end.getNumero());
		pst.setString(7, end.getBairro());
		pst.setString(8, end.getCEP());
		pst.setInt(9, end.getCidade().getId());
		pst.setInt(10, end.getEstado().getId());
		pst.setInt(11, end.getPais().getId());
		pst.setString(12, end.getObservacoes());
		
		
		
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

		Endereco end = (Endereco)entidade;
		
		PreparedStatement pst=null;
		openConnection();
		connection.setAutoCommit(false); //Impede o auto commit no banco de dados.
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT tend.id, tend.alias, tend.tipoend, tend.tipologradouro, tend.logradouro, tend.numero, tend.bairro, tend.cep, tcidade.id, tcidade.nome, testado.id, testado.nome, tpais.id, tpais.nome, tend.obs, ttipoend.nome, ttipologra.nome");
		sql.append(" FROM (((((tend ");
		sql.append("INNER JOIN tcidade ON tend.cidade = tcidade.id)");
		sql.append("INNER JOIN testado ON tend.estado = testado.id)");
		sql.append("INNER JOIN tpais ON tend.pais = tpais.id)");
		sql.append("INNER JOIN ttipoend ON tend.tipoend = ttipoend.id)");
		sql.append("INNER JOIN ttipologra ON tend.tipologradouro = ttipologra.id)");
		sql.append(" WHERE cliente=?");
		
		pst = connection.prepareStatement(sql.toString(), 
				Statement.RETURN_GENERATED_KEYS);
		
		pst.setInt(1, end.getCliente());
		
		ResultSet rs = pst.executeQuery();
		
		List<EntidadeDominio> enderecos = new ArrayList<EntidadeDominio>();
		
		while(rs.next()){
			
			Endereco e = new Endereco();
			Cidade c = new Cidade();
			Estado est = new Estado();
			Pais p = new Pais();
			TipoEndereco te = new TipoEndereco();
			TipoLogradouro tl = new TipoLogradouro();
			
			e.setId(rs.getInt(1));
			e.setAlias(rs.getString(2));
			te.setId(rs.getInt(3));
			tl.setId(rs.getInt(4));
			e.setLogradouro(rs.getString(5));
			e.setNumero(rs.getString(6));
			e.setBairro(rs.getString(7));
			e.setCEP(rs.getString(8));			
			c.setId(rs.getInt(9));
			c.setNome(rs.getString(10));			
			est.setId(rs.getInt(11));
			est.setNome(rs.getString(12));
			p.setId(rs.getInt(13));
			p.setNome(rs.getString(14));
			e.setObservacoes(rs.getString(15));
			te.setNome(rs.getString(16));
			tl.setNome(rs.getString(17));
			
			e.setCidade(c);
			e.setEstado(est);
			e.setPais(p);
			e.setTipologra(tl);
			e.setTipoend(te);
			
			enderecos.add(e);
			
		}
		
		pst.close();
		connection.commit();
		connection.close();
		return enderecos;
	}

}
