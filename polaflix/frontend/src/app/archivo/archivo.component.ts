import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
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
  expandirTodas = signal<boolean>(false);

  archivadas = computed(() => {
    const u = this.usuarioData();
    const s = this.seriesCat();
    if (!u || !s || !u.seriesArchivadas) return [];
    return s.filter(serie => u.seriesArchivadas.includes(serie.id));
  });

  constructor(
    private polaflixService: PolaflixService,
    private router: Router
  ) {}

  ngOnInit() {
    const u = sessionStorage.getItem('usuario') || 'carlosalarcon';
    if (!u) {
      this.router.navigate(['/']);
      return;
    }
    this.username.set(u);

    
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

  //Actividad de expandir todas rapidanmente
  toggleExpandirTodas() {
    const mostrar = !this.expandirTodas();
    this.expandirTodas.set(mostrar);
    if (!mostrar) {
      this.expandidaId.set(null);
    } else {  
      for (const serie of this.archivadas()) {
        if (!this.detallesExtra()[serie.id]) {
          this.polaflixService.getSerie(serie.id).subscribe(detalles => {
            this.detallesExtra.update(prev => ({ ...prev, [serie.id]: detalles }));
          });
        }
      }}
  }
  //

  desarchivar(idSerie: number, event: Event) {
    event.stopPropagation();
    this.polaflixService.desarchivarSerie(this.username(), idSerie).subscribe(() => {
      const u = this.usuarioData();
      if (u && u.seriesArchivadas) {
        u.seriesArchivadas = u.seriesArchivadas.filter((id: number) => id !== idSerie);
        this.usuarioData.set({ ...u }); 
      }
    });
  }
}