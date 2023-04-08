import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SearchreviewComponent } from './components/searchreview.component';
import { MoviereviewlistComponent } from './components/moviereviewlist.component';
import { PostcommentComponent } from './components/postcomment.component';

const routes: Routes = [
  { path: '', component: SearchreviewComponent },
  { path: 'search', component: MoviereviewlistComponent },
  { path: 'comment/:movieName', component: PostcommentComponent },
  { path: '**', redirectTo: '/', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
