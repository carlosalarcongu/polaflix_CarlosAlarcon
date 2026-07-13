import { Component, OnInit, Inject, PLATFORM_ID, ChangeDetectorRef } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { PolaflixService } from '../polaflix.service';

@Component({
  selector: 'app-serie',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './serie.component.html',
  styleUrls: ['./serie.component.css']
})
export class SerieComponent implements OnInit {
  serie: any;
  usuario: any;
  username: string = '';
  temporadaExpandida: number | null = null;
  

  constructor(
    private route: ActivatedRoute, 
    private router: Router,
    private polaflixService: PolaflixService,
    @Inject(PLATFORM_ID) private platformId: Object,
    private cdr: ChangeDetectorRef // <-- Añadido
  ) {}

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.username = sessionStorage.getItem('usuario') || '';
      
      if (!this.username) { 
        this.router.navigate(['/']); 
        return; 
      }

      const idSerie = Number(this.route.snapshot.paramMap.get('id'));
      
      this.polaflixService.getSerie(idSerie).subscribe(s => {
        this.serie = s;
        if (s.temporadas && s.temporadas.length > 0) {
            // Expandimos por "numero", no por "id"
            this.temporadaExpandida = s.temporadas[0].numero; 
        }
        this.cdr.detectChanges(); // Forzamos dibujado
      });
      
      this.polaflixService.entrar(this.username).subscribe(u => {
        this.usuario = u;
        this.cdr.detectChanges(); // Forzamos dibujado
      });
    }
  }

  toggleTemporada(numeroTemp: number) {
      if (this.temporadaExpandida === numeroTemp) {
          this.temporadaExpandida = null;
      } else {
          this.temporadaExpandida = numeroTemp;
      }
      this.cdr.detectChanges(); // Forzamos dibujado
  }
  

  esVisto(idCapitulo: number): boolean {
    if (!this.usuario || !this.usuario.capitulosVistos) return false;
    return this.usuario.capitulosVistos.some((c: any) => c.id === idCapitulo);
  }

  marcarVisto(idCapitulo: number) {
    if (isPlatformBrowser(this.platformId)) {
      this.polaflixService.marcarComoVisto(this.username, idCapitulo).subscribe(() => {
        this.polaflixService.entrar(this.username).subscribe(u => {
          this.usuario = u;
          this.cdr.detectChanges(); // Forzamos dibujado
        });
      });
    }
  }

}