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
  
  downloadPdf() {
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
  }

  /*baixarPdf() {
    const conteudo = document.getElementById('conteudo-pdf')?.innerHTML || '';

    const body = { html: conteudo };

    this.http.post('http://localhost:8080/api/generate-pdf', body, { responseType: 'blob' })
      .subscribe((response: Blob) => {
        const url = window.URL.createObjectURL(response);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'jornal-noticias.pdf';
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
      });
  }*/

}
