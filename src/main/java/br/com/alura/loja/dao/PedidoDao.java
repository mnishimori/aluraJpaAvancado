package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

public class PedidoDao {

	private EntityManager em;

	public PedidoDao(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Pedido pedido) {
		this.em.persist(pedido);
	}
	
	public BigDecimal valorTotalVendido() {
		String jpql = "select sum(p.valorTotal) from Pedido p";
		return this.em.createQuery(jpql, BigDecimal.class)
				.getSingleResult();
	}
	
	public List<RelatorioDeVendasVo> relatorioDeVendas() {
		StringBuilder sb = new StringBuilder("select ");
		sb.append("new br.com.alura.loja.vo.RelatorioDeVendasVo(produto.nome, sum(item.quantidade), max(pedido.data)) ");
		sb.append("from Pedido pedido ");
		sb.append("join pedido.itens item ");
		sb.append("join item.produto produto ");
		sb.append("group by produto.nome ");
		sb.append("order by sum(item.quantidade) desc");
		
		return this.em.createQuery(sb.toString(), RelatorioDeVendasVo.class).getResultList(); 
	}

}
