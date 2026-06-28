import { Component, OnInit, Inject, PLATFORM_ID, signal, computed } from '@angular/core';
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
  username = signal<string>('');
  usuarioData = signal<any>(null);
  seriesData = signal<any[]>([]);

  empezadas = computed(() => this.filtrarSeries('EMPEZADA'));
  pendientes = computed(() => this.filtrarSeries('PENDIENTE'));
  terminadas = computed(() => this.filtrarSeries('TERMINADA'));

  constructor(
    private polaflixService: PolaflixService,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit() {
    if (isPlatformBrowser(this.platformId)) {
      const u = sessionStorage.getItem('usuario') || '';
      if (!u) {
        this.router.navigate(['/']);
        return;
      }
      this.username.set(u);
      this.cargarDatos();
    }
  }

  cargarDatos() {
    forkJoin({
      usuario: this.polaflixService.entrar(this.username()),
      series: this.polaflixService.getSeries()
    }).subscribe(({usuario, series}) => {
      this.usuarioData.set(usuario);
      this.seriesData.set(series);
    });
  }

  filtrarSeries(estadoDeseado: string) {
    const u = this.usuarioData();
    const s = this.seriesData();
    if (!u || !s) return [];
    
    return s.filter(serie => {
      const estaEnEstado = u.estadoSeries?.[serie.id] === estadoDeseado;
      const estaArchivada = u.seriesArchivadas?.includes(serie.id);
      return estaEnEstado && !estaArchivada;
    });
  }

  archivar(idSerie: number, event: Event) {
    event.preventDefault();
    event.stopPropagation();
    this.polaflixService.archivarSerie(this.username(), idSerie).subscribe(() => {
      this.cargarDatos();
    });
  }
}