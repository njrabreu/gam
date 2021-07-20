import { NgModule } from '@angular/core';

import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';

import { GameService } from './services/game.service';
import { AuthInterceptorService } from './services/auth-interceptor.service';

import { AppComponent } from './app.component';
import { GameComponent } from './components/game/game.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { ErrorpageComponent } from './components/errorpage/errorpage.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { LoginLayoutComponent } from './components/login-layout/login-layout.component';
import { MainLayoutComponent } from './components/main-layout/main-layout.component';

@NgModule({
	declarations: [
		AppComponent,
		GameComponent,
		HomeComponent,
		LoginComponent,
		ErrorpageComponent,
  HeaderComponent,
  FooterComponent,
  LoginLayoutComponent,
  MainLayoutComponent
	],
	imports: [
		BrowserModule,
		AppRoutingModule,
		HttpClientModule,
		ReactiveFormsModule
	],
	providers: [GameService, DatePipe,
		{
			provide: HTTP_INTERCEPTORS, useClass: AuthInterceptorService, multi: true
		}],
	bootstrap: [AppComponent]
})
export class AppModule { }
