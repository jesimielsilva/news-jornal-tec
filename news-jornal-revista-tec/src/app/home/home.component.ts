import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  constructor (private http: HttpClient) {}

  ngInit(){}
  
  baixarPdf() {
    // Pega o conteúdo HTML do elemento com ID "conteudo-pdf" ou uma string vazia, se o elemento não for encontrado
    const conteudo = document.getElementById('conteudo-pdf')?.innerHTML || '';

    // Cria um objeto de corpo para a requisição POST contendo o HTML
    const body = { html: conteudo };

    // Faz uma requisição HTTP POST para o backend na URL 'http://localhost:8080/api/generate-pdf'
    // O parâmetro 'responseType: blob' indica que a resposta será um arquivo binário (blob), ou seja, o PDF
    this.http.post('http://localhost:8080/api/generate-pdf', body, { responseType: 'blob' })
      .subscribe((response: Blob) => {
        // Quando a resposta chegar, cria uma URL temporária para o PDF gerado
        const url = window.URL.createObjectURL(response);
        // Cria um elemento de link <a> dinamicamente
        const a = document.createElement('a');
        // Define o atributo href do link com a URL temporária do arquivo PDF
        a.href = url;
        // Define o nome do arquivo para ser baixado (jornal-noticias.pdf)
        a.download = 'jornal-noticias.pdf';
        // Adiciona o elemento <a> ao corpo do documento para permitir que o clique funcione
        document.body.appendChild(a);
        // Simula um clique no link para iniciar o download do PDF
        a.click();
        // Remove o elemento <a> do DOM após o clique, limpando a página
        document.body.removeChild(a);
      });
  }
  
  /*downloadPdf() {
    // Faz uma requisição GET para o endpoint da API que gera o PDF
    // A opção responseType: 'blob' indica que esperamos uma resposta binária (arquivo)
    this.http.get('http://localhost:8080/api/generate-pdf', { responseType: 'blob' })
      .subscribe((response: Blob) => {
        // Cria uma URL temporária para o Blob recebido
        const url = window.URL.createObjectURL(response);
        // Cria um elemento <a> dinamicamente
        const a = document.createElement('a');
        // Define o href da tag <a> para a URL temporária criada
        a.href = url;
        // Define o nome do arquivo que será baixado como 'jornal-noticias.pdf'
        a.download = 'jornal-noticias.pdf';
        // Adiciona o elemento <a> ao corpo do documento
        document.body.appendChild(a);
        // Simula um clique no link <a> para iniciar o download
        a.click();
        // Remove o elemento <a> do DOM após o clique para limpar o documento
        document.body.removeChild(a);
      });
  }*/  

}
