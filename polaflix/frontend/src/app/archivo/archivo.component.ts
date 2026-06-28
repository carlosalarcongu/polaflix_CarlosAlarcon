import { Component, OnInit, Inject, PLATFORM_ID, signal, computed } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { Router } from '@angular/router';
import { PolaflixService } from '../polaflix.service';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-archivo',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './archivo.component.html'
})
export class ArchivoComponent implements OnInit {
  username = signal<string>('');
  usuarioData = signal<any>(null);
  seriesCat = signal<any[]>([]);
  detallesExtra = signal<Record<number, any>>({});
  expandidaId = signal<number | null>(null);

  archivadas = computed(() => {
    const u = this.usuarioData();
    const s = this.seriesCat();
    if (!u || !s || !u.seriesArchivadas) return [];
    return s.filter(serie => u.seriesArchivadas.includes(serie.id));
  });

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
      this.seriesCat.set(series);
    });
  }

  toggleDetalle(idSerie: number) {
    if (this.expandidaId() === idSerie) {
      this.expandidaId.set(null);
    } else {
      this.expandidaId.set(idSerie);
      if (!this.detallesExtra()[idSerie]) {
        this.polaflixService.getSerie(idSerie).subscribe(detalles => {
          this.detallesExtra.update(prev => ({ ...prev, [idSerie]: detalles }));
        });
      }
    }
  }

  desarchivar(idSerie: number, event: Event) {
    event.stopPropagation();
    this.polaflixService.desarchivarSerie(this.username(), idSerie).subscribe(() => {
      this.cargarDatos();
    });
  }
}