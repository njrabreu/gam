import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { LoginLayoutComponent } from './components/login-layout/login-layout.component';
import { MainLayoutComponent } from './components/main-layout/main-layout.component';
import { HomeComponent } from './components/home/home.component';
import { GameComponent } from './components/game/game.component';
import { ErrorpageComponent } from './components/errorpage/errorpage.component';

/* Path order must be from the less specific to the most specific */
const routes: Routes = [
	{
		path: '',
		component: LoginLayoutComponent,
		children: [
			{ path: '', component: LoginComponent, pathMatch: 'full' }
		]
	},
	{
		path: 'allgames',
		component: MainLayoutComponent,
		children: [
			{ path: '', component: HomeComponent, pathMatch: 'full' }
		]
	},
	{
		path: 'game/:id',
		component: MainLayoutComponent,
		children: [
			{ path: '', component: GameComponent, pathMatch: 'full' }
		]
	},
	{
		path: 'error',
		component: ErrorpageComponent
	}
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }
