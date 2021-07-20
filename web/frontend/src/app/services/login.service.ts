import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
	providedIn: 'root'
})
export class LoginService {

	constructor(private http: HttpClient, private router: Router) { }

	checkUser(id: string, pwd: string) {
		let url = 'server/user/check?id=' + id + "&pwd=" + pwd;
		return this.http.get(url);
	}

	registerUser(id: string, pwd: string) {
		let url = 'server/user/register?id=' + id + "&pwd=" + pwd;
		return this.http.post(url, null);
	}

	validateUserInSession(id: string) {
		if (id == null) {
			console.log("User not authenticated. Redirecting to login page...");
			this.router.navigateByUrl('/');
		}
	}

	clearLocalStorage() {
		localStorage.removeItem('id');
		localStorage.removeItem('token');
	}

	logout() {
		console.log("User logged out. Redirecting to login page...");

		this.clearLocalStorage();
		this.router.navigateByUrl('/');
	}

	showErrorPage() {
		console.log("Error with user credentials. Redirecting to error page...");

		this.clearLocalStorage();
		this.router.navigateByUrl('/error');
	}
}
