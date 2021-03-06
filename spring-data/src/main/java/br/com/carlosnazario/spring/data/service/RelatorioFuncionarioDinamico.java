package br.com.carlosnazario.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.carlosnazario.spring.data.orm.Funcionario;
import br.com.carlosnazario.spring.data.repository.FuncionarioRepository;
import br.com.carlosnazario.spring.data.specification.SpecificationFuncionario;

@Service
public class RelatorioFuncionarioDinamico {

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private final FuncionarioRepository funcionarioRepository;
	
	public RelatorioFuncionarioDinamico(FuncionarioRepository funcionarioRepository) {
		 this.funcionarioRepository = funcionarioRepository;
	}
	
	public void inicial(Scanner scanner) {
		System.out.println("Digite o nome do funcionario: ");
		String nome = scanner.next();
		if(nome.equalsIgnoreCase("NULL")) {
			nome = null;
		}
		
		System.out.println("Digite o CPF do funcionario: ");
		String cpf = scanner.next();
		if(cpf.equalsIgnoreCase("NULL")) {
			cpf = null;
		}
		
		System.out.println("Digite o salario do funcionario: ");
		Double salario = scanner.nextDouble();
		if(salario == 0) {
			salario = null;
		}
		
		System.out.println("Digite a data de contratacao do funcionario: ");
		String data = scanner.next();
		LocalDate dataContratacao;
		
		if(data.equalsIgnoreCase("NULL")) {
			dataContratacao = null;
		} else {
			dataContratacao = LocalDate.parse(data, formatter);
		}
		
		List<Funcionario> funcionarios = funcionarioRepository
				.findAll(Specification
				.where(
					SpecificationFuncionario.nome(nome))
					.or(SpecificationFuncionario.cpf(cpf))
					.or(SpecificationFuncionario.salario(salario))
					.or(SpecificationFuncionario.dataContratacao(dataContratacao))
				); 
		funcionarios.forEach(System.out::println);
	}
}
