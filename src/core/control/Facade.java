package core.control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.app.DomainEntity;
import core.app.Result;
import core.control.IFacade;
import core.businesslogic.*;
import core.app.Result;
import core.dao.*;

import core.domain.*;



public class Facade implements IFacade {

	
	private Map<String, IDAO> daos;
	
	
	private Map<String, Map<String, List<IStrategy>>> rns;
	
	private Result result;
	
	
	public Facade(){
		
		daos = new HashMap<String, IDAO>();
		
		rns = new HashMap<String, Map<String, List<IStrategy>>>();
			
		AutorDAO autDAO = new AutorDAO(null,null);
		BandeiraCardDAO bcDAO = new BandeiraCardDAO(null ,null);
		CardDAO carddao = new CardDAO("tcard","id");
		CategoriaDAO catDAO = new CategoriaDAO(null, null);
		CidadeDAO cDAO = new CidadeDAO(null, null);
		ClienteDAO cliDAO = new ClienteDAO(null, null);
		CupomPromoDAO cupromoDAO = new CupomPromoDAO(null,null);
		CupomTrocaDAO cuptrocadao = new CupomTrocaDAO(null, null);
		EditoraDAO edtDAO = new EditoraDAO(null,null);
		EnderecoDAO enddao = new EnderecoDAO("tend","id");
		EstadoDAO eDAO = new EstadoDAO(null, null);
		EstoqueDAO estDAO = new EstoqueDAO(null,null);
		GrupoPrecificacaoDAO grpDAO = new GrupoPrecificacaoDAO(null, null);		
		ItemDAO itDAO = new ItemDAO(null,null);
		ItemCarrinhoDAO  icDAO = new ItemCarrinhoDAO(null,null);
		LivroDAO livDAO = new LivroDAO(null, null);
		PedidoDAO pedDAO = new PedidoDAO(null,null);
		PaisDAO pDAO = new PaisDAO(null, null);
		SubCategoriaDAO subcatDAO = new SubCategoriaDAO(null, null);
		TrocaItemDAO trocait = new TrocaItemDAO(null, null);
		TrocaPedidoDAO trocaped = new TrocaPedidoDAO(null, null);
		UserDAO uDAO = new UserDAO(null,null);
		DadosAnaliseDAO dataDAO = new DadosAnaliseDAO(null, null);
		
		AtivacaoDAO atvDAO = new AtivacaoDAO(null,null);
		InativacaoDAO intvDAO = new InativacaoDAO(null,null);
		
		CategoriaAtivaDAO cateativDAO = new CategoriaAtivaDAO(null, null);
		CategoriaInativaDao cateinativDAO = new CategoriaInativaDao(null, null);
		
		daos.put(Estoque.class.getName(), estDAO);
		daos.put(Livro.class.getName(), livDAO); 
		daos.put(Categoria.class.getName(), catDAO);
		daos.put(SubCategoria.class.getName(), subcatDAO);
		daos.put(GrupoPreco.class.getName(), grpDAO);
		daos.put(CategoriaAtiva.class.getName(), cateativDAO);
		daos.put(CategoriaInativa.class.getName(), cateinativDAO);
		daos.put(AtivaSituacao.class.getName(), atvDAO);
		daos.put(InativaSituacao.class.getName(), intvDAO);
		daos.put(Cliente.class.getName(), cliDAO);
		daos.put(Autor.class.getName(), autDAO);
		daos.put(Editora.class.getName(), edtDAO);
		daos.put(Pais.class.getName(), pDAO);
		daos.put(Estado.class.getName(), eDAO);
		daos.put(Cidade.class.getName(), cDAO);
		daos.put(BandeiraCard.class.getName(), bcDAO);
		daos.put(Item.class.getName(), itDAO);
		daos.put(User.class.getName(), uDAO);
		daos.put(ItemCarrinho.class.getName(), icDAO);
		daos.put(Pedido.class.getName(), pedDAO);
		daos.put(CupomPromo.class.getName(), cupromoDAO);
		daos.put(TrocaItem.class.getName(), trocait);
		daos.put(TrocaPedido.class.getName(), trocaped);
		daos.put(Card.class.getName(), carddao);
		daos.put(Endereco.class.getName(), enddao);
		daos.put(CupomTroca.class.getName(), cuptrocadao);
		daos.put(DadosAnalise.class.getName(), dataDAO);
		
		
		
		CriarLog clog = new CriarLog();
		ValidarCpf valcpf = new ValidarCpf();
		ConfirmaSenha confsenha = new ConfirmaSenha();
		GerarCupomTrocaItem gerarcpmtrocait = new GerarCupomTrocaItem();
		GerarCupomTrocaPedido gerarcpmped = new GerarCupomTrocaPedido();
		ValidarLivroDisponivel validlivrodisp = new ValidarLivroDisponivel();
		ValidarValorPagamento validarpagamento = new ValidarValorPagamento();
		ValidarCupom validarCupom = new ValidarCupom();
		ValidarAlteracaoReserva validarAlteraReserv = new ValidarAlteracaoReserva();
		ValorMinimoCard valormin = new ValorMinimoCard();
		
		//Livro___________		
		List<IStrategy> rnsAlterarLivro = new ArrayList<IStrategy>();
		rnsAlterarLivro.add(clog);		
		Map<String, List<IStrategy>> rnsLivro = new HashMap<String, List<IStrategy>>();		
		rnsLivro.put("ALTERAR", rnsAlterarLivro);		
		rns.put(Livro.class.getName(), rnsLivro);
		
		//____________Categoria		
		List<IStrategy> rnsSalvarCategoria = new ArrayList<IStrategy>();
		rnsSalvarCategoria.add(clog);		
		Map<String, List<IStrategy>> rnsCategoria = new HashMap<String, List<IStrategy>>();
		rnsCategoria.put("SALVAR", rnsSalvarCategoria);		
		rns.put(Categoria.class.getName(), rnsCategoria);	
		
		//Cliente_____________________		
		List<IStrategy> rnsSalvarCliente = new ArrayList<IStrategy>();
		rnsSalvarCliente.add(valcpf);
		rnsSalvarCliente.add(clog);
		rnsSalvarCliente.add(confsenha);
		Map<String, List<IStrategy>> rnsCliente = new HashMap<String, List<IStrategy>>();
		rnsCliente.put("SALVAR", rnsSalvarCliente);
		rns.put(Cliente.class.getName(), rnsCliente);
		
		//__________________________TROCAITEM
		List<IStrategy> rnsAlterarTrocaItem = new ArrayList<IStrategy>();
		rnsAlterarTrocaItem.add(gerarcpmtrocait);
				
		Map<String, List<IStrategy>> rnsTrocaitem = new HashMap<String, List<IStrategy>>();
		rnsTrocaitem.put("ALTERAR", rnsAlterarTrocaItem);
		rns.put(TrocaItem.class.getName(),rnsTrocaitem);
		
		//ITEMCARRINHO____________________________
		List<IStrategy> rnsSalvarItemCarrinho = new ArrayList<IStrategy>();
		rnsSalvarItemCarrinho.add(validlivrodisp);
		
		List<IStrategy> rnsAlterarItemCarrinho = new ArrayList<IStrategy>();
		rnsAlterarItemCarrinho.add(validarAlteraReserv);
		
		Map<String, List<IStrategy>> rnsItemCarrinho = new HashMap<String, List<IStrategy>>();
		rnsItemCarrinho.put("SALVAR", rnsSalvarItemCarrinho);
		rnsItemCarrinho.put("ALTERAR", rnsAlterarItemCarrinho);
		rns.put(ItemCarrinho.class.getName(), rnsItemCarrinho);
		
		
		//_______________________________PEDIDO		
		List<IStrategy> rnsAlterarPedido = new ArrayList<IStrategy>();
		rnsAlterarPedido.add(validarpagamento);
		rnsAlterarPedido.add(validarCupom);
		rnsAlterarPedido.add(valormin);
		Map<String, List<IStrategy>> rnsPedido = new HashMap<String, List<IStrategy>>();
		rnsPedido.put("ALTERAR", rnsAlterarPedido);
		rns.put(Pedido.class.getName(), rnsPedido);
		
		
		//TROCAPEDIDO______________________________
		List<IStrategy> rnsAlterarTrocaPedido = new ArrayList<IStrategy>();
		rnsAlterarTrocaPedido.add(gerarcpmped);
		Map<String, List<IStrategy>> rnsTrocaPedido = new HashMap<String, List<IStrategy>>();
		rnsTrocaPedido.put("ALTERAR", rnsAlterarTrocaPedido);
		rns.put(TrocaPedido.class.getName(), rnsTrocaPedido);
		
	}
	
	
	@Override
	public Result save(DomainEntity entity) {
		
		result = new Result();
		
		
		String nmClasse = entity.getClass().getName();	
		
		String msg = executeLogic(entity, "SAVE");
		
				
		if(msg == null){
			IDAO dao = daos.get(nmClasse);
			try {
				dao.save(entity);
				List<DomainEntity> entidades = new ArrayList<DomainEntity>();
				entidades.add(entity);
				result.setEntidades(entidades);
			} catch (SQLException e) {
				e.printStackTrace();
				result.setMsg("N�o foi poss�vel realizar o registro!");
				
			}
		}else{
			result.setMsg(msg);
					
			
		}
		
		return result;
	}

	@Override
	public Result update(DomainEntity entity) {
		result = new Result();
		String nmClasse = entity.getClass().getName();	
		
		String msg = executeLogic(entity, "ALTERAR");
	
		if(msg == null){
			IDAO dao = daos.get(nmClasse);
			try {
				dao.update(entity);
				List<DomainEntity> entidades = new ArrayList<DomainEntity>();
				entidades.add(entity);
				result.setEntidades(entidades);
			} catch (SQLException e) {
				e.printStackTrace();
				result.setMsg("N�o foi poss�vel realizar o registro!");
				
			}
		}else{
			result.setMsg(msg);
					
			
		}
		
		return result;

	}

	@Override
	public Result delete(DomainEntity entity) {
		result = new Result();
		String nmClasse = entity.getClass().getName();	
		
		String msg = executeLogic(entity, "EXCLUIR");
		
		
		if(msg == null){
			IDAO dao = daos.get(nmClasse);
			try {
				dao.delete(entity);
				List<DomainEntity> entidades = new ArrayList<DomainEntity>();
				entidades.add(entity);
				result.setEntidades(entidades);
			} catch (SQLException e) {
				e.printStackTrace();
				result.setMsg("N�o foi poss�vel realizar o registro!");
				
			}
		}else{
			result.setMsg(msg);
					
			
		}
		
		return result;

	}

	@Override
	public Result read(DomainEntity entity) {
		result = new Result();
		String nmClasse = entity.getClass().getName();	
		
		String msg = executeLogic(entity, "EXCLUIR");
				
		if(msg == null){
			IDAO dao = daos.get(nmClasse);
			try {
				
				result.setEntidades(dao.read(entity));
					
				
			} catch (SQLException e) {
				e.printStackTrace();
				result.setMsg("N�o foi poss�vel realizar a consulta!");
				
			}
		}else{
			result.setMsg(msg);
			
		}
		
		return result;

	}
	
	@Override
	public Result visualizar(DomainEntity entity) {
		result = new Result();
		String nmClasse = entity.getClass().getName();	
		
		String msg = executeLogic(entity, "EXCLUIR");
				
		if(msg == null){
			IDAO dao = daos.get(nmClasse);
			try {
				
				result.setEntidades(dao.read(entity));
				
				
			} catch (SQLException e) {
				e.printStackTrace();
				result.setMsg("N�o foi poss�vel realizar a consulta!");
				
			}
		}else{
			result.setMsg(msg);
			
		}
		return result;

	}

	
	private String executeLogic(DomainEntity entidade, String operacao){
		String nmClasse = entidade.getClass().getName();		
		StringBuilder msg = new StringBuilder();
		
		Map<String, List<IStrategy>> regrasOperacao = rns.get(nmClasse);
		
	
		
		
		if(regrasOperacao != null){
			List<IStrategy> regras = regrasOperacao.get(operacao);
			
			if(regras != null){
				for(IStrategy s: regras){			
					String m = s.processar(entidade);			
					
					if(m != null){
						msg.append(m);
						msg.append("\n");
					}			
				}	
			}			
			
		}
		
		if(msg.length()>0)
			return msg.toString();
		else
			return null;
	}


	



}
