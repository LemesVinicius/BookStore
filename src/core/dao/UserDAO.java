package core.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import les.dominio.Cliente;
import les.dominio.EntidadeDominio;
import les.dominio.Pessoa;
import les.dominio.User;

public class UserDAO extends AbstractJdbcDAO{

	public UserDAO(String table, String idTable) {
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
		
		User u = (User)entidade;
		
		List <EntidadeDominio> user = new ArrayList<EntidadeDominio>();
		
				
		
		if(u.getTipo().equals("administrador")){
			
			
			
		}if(u.getTipo().equals("gerente")){
			
			
		}if(u.getTipo().equals("cliente")){
			
			Cliente cli = new Cliente();
			cli.setEmail(u.getLogin());
			cli.setSenha(u.getSenha());
		
			ClientDAO clidao = new ClientDAO(null, null);
			u.setDados((Pessoa)clidao.consultar(cli).get(0));
			
			
		}
		
		user.add(u);
		
		
		return user;
	}

}
