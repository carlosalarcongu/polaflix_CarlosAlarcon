import { Routes } from '@angular/router';
import { InicioComponent } from './inicio/inicio.component';
import { SerieComponent } from './serie/serie.component';
import { ArchivoComponent } from './archivo/archivo.component';

export const routes: Routes = [
  { path: '', redirectTo: 'inicio', pathMatch: 'full' },
  { path: 'inicio', component: InicioComponent },
  { path: 'archivo', component: ArchivoComponent },
  { path: 'serie/:id', component: SerieComponent },
  { path: '**', redirectTo: 'inicio' }
];