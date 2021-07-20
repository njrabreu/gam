import { Component, OnInit } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

	token: string = "";
	validMessage: string = "";

	loginform: FormGroup =
		new FormGroup({
			login: new FormControl('', Validators.required),
			password: new FormControl('', Validators.required)
		});

	constructor(private loginService: LoginService, private router: Router) { }

	ngOnInit(): void {

	}

	proceedWithLogin() {

		let currentUser: string = localStorage.getItem('id') as string;

		console.log('Current user is: ' + currentUser);

		if (this.loginform.controls['login'].value === currentUser) {
			this.validMessage = "Login OK!";
			this.router.navigateByUrl('/allgames');
		}
		else {
			this.validMessage = "Invalid login!";
		}
	}

	tryLogin() {

		this.loginService.checkUser(this.loginform.controls['login'].value, this.loginform.controls['password'].value).subscribe(
			data => {
				localStorage.setItem('id', (data as any).id);
				localStorage.setItem('token', (data as any).token);
				this.proceedWithLogin();
			},
			err => console.error(err),
			() => { console.log("tryLogin: answer complete!") }
		);
	}

	registerLogin() {

		this.loginService.registerUser(this.loginform.controls['login'].value, this.loginform.controls['password'].value).subscribe(
			data => {
				this.validMessage = "User registered!";
			},
			err => console.error(err),
			() => {
				console.log("registerLogin: answer complete!");
			}
		);
	}
	
	
	clearLabel() {
		this.validMessage = "";
	}
}
