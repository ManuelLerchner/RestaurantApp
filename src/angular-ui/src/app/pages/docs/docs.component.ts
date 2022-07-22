import { Component, OnInit } from '@angular/core';
declare const SwaggerUIBundle: any;

@Component({
  selector: 'app-docs',
  templateUrl: './docs.component.html',
  styleUrls: ['./docs.component.scss']
})
export class DocsComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    const ui = SwaggerUIBundle({
      dom_id: '#swagger-ui',
      layout: 'BaseLayout',
      presets: [
        SwaggerUIBundle.presets.apis,
        SwaggerUIBundle.SwaggerUIStandalonePreset
      ],
      url: 'https://api.manuellerchner.de/static/restaurantapplication-docs.json',
      docExpansion: 'none',
      operationsSorter: 'alpha'
    });
  }

}
