import { Component, OnInit, Inject, PLATFORM_ID, ChangeDetectorRef } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { PolaflixService } from '../polaflix.service';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-inicio',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.css']
})
export class InicioComponent implements OnInit {
  username: string = '';
  empezadas: any[] = [];
  pendientes: any[] = [];
  terminadas: any[] = [];

  constructor(
    private polaflixService: PolaflixService, 
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object,
    private cdr: ChangeDetectorRef // <-- Añadido
  ) {}

  ngOnInit() {
    if (isPlatformBrowser(this.platformId)) {
      this.username = sessionStorage.getItem('usuario') || '';
      
      if (!this.username) {
        this.router.navigate(['/']);
        return;
      }

      forkJoin({
        usuario: this.polaflixService.entrar(this.username),
        series: this.polaflixService.getSeries()
      }).subscribe(({usuario, series}) => {
        this.empezadas = [];
        this.pendientes = [];
        this.terminadas = [];
        
        if (usuario.estadoSeries) {
          Object.keys(usuario.estadoSeries).forEach(idStr => {
            const id = parseInt(idStr, 10);
            const estado = usuario.estadoSeries[idStr];
            const serie = series.find(s => s.id === id);
            
            if (serie) {
              if (estado === 'EMPEZADA') this.empezadas.push(serie);
              if (estado === 'PENDIENTE') this.pendientes.push(serie);
              if (estado === 'TERMINADA') this.terminadas.push(serie);
            }
          });
        }
        // Obligamos a Angular a actualizar la pantalla inmediatamente
        this.cdr.detectChanges(); 
      });
    }
  }

  salir() {
    if (isPlatformBrowser(this.platformId)) {
      sessionStorage.clear();
    }
    this.router.navigate(['/']);
  }
}