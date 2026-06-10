import { Routes } from '@angular/router';
import { EntradaComponent } from './entrada/entrada.component';
import { InicioComponent } from './inicio/inicio.component';
import { SerieComponent } from './serie/serie.component';

export const routes: Routes = [
  { path: '', component: EntradaComponent },
  { path: 'inicio', component: InicioComponent },
  { path: 'serie/:id', component: SerieComponent },
  { path: '**', redirectTo: '' }
];