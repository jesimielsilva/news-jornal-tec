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
    this.http.get('http://localhost:8080/api/generate-pdf', { responseType: 'blob' })
      .subscribe((response: Blob) => {
        const url = window.URL.createObjectURL(response);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'jornal-noticias.pdf';
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
      });
  }

  baixarPdf() {
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
  }

}
