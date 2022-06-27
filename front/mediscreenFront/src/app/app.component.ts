import {Component, OnInit} from '@angular/core';
import {Patient} from "./patient";
import {PatientService} from "./patient.service";
import {HttpErrorResponse} from "@angular/common/http";
import {NgForm} from "@angular/forms";
import {HistoryService} from "./history.service";
import {History, Note} from "./History";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  public editPatient: Patient | undefined;
  public getPatient: Patient | undefined;
  public getHistory: History | undefined;
  public getNotes: Note[] = [];
  public actualNote: Note | undefined;

  constructor(private patientService: PatientService, private historyService: HistoryService) {
  }

  ngOnInit() {
  }

  public getPatientByName(familyName: string, givenName: string): void {

    this.patientService.getPatient(familyName, givenName).subscribe({
      next: (response: Patient) => {
        this.getPatient = response;
        this.getHistoryByName(familyName, givenName);
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    })
  }

  public onAddPatient(addForm: NgForm): void {
    document.getElementById('add-patient-form')?.click();
    this.patientService.addPatient(addForm.value).subscribe({
      next: (response: Patient) => {
        console.log(response);
        this.onOpenModal('addHistory', response);
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    })
  }

  public onUpdatePatient(patient: Patient): void {
    this.editPatient = patient;
    this.patientService.updatePatient(patient).subscribe({
      next: (response: Patient) => {
        console.log(response);
        this.onUpdateHistory(response);
        this.getPatientByName(patient.familyName, patient.givenName);
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    })
  }

  public onUpdateHistory(patient: Patient): void {
    this.historyService.updateHistory(this.copyUpdatePatientToHistory(patient)).subscribe({
      next: (response) => {
        console.log(response);
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    });
  }

  public copyUpdatePatientToHistory(patient: Patient): History {
    return {
      patientId: patient.id,
      familyName: patient.familyName,
      givenName: patient.givenName,
      notes: []
    };
  }

  public onOpenModal(mode: string, patient?: Patient): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addPatientModal');
    }
    if (mode === 'edit') {
      this.editPatient = patient;
      button.setAttribute('data-target', '#updatePatientModal');
    }
    if (mode === 'addHistory') {
      this.getPatient = patient;
      button.setAttribute('data-target', '#addHistoryModal');
    }
    if (mode === 'addNote') {
      button.setAttribute('data-target', '#addNoteModal');
    }
    container?.appendChild(button);
    button.click();
  }

  public onOpenModalNote(mode: string, note?: Note) {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'editNote') {
      console.log("ici         " + note?.text);
      this.actualNote = note;
      button.setAttribute('data-target', '#updateNoteModal');
    }
    container?.appendChild(button);
    button.click();
  }

  public getHistoryByName(familyName: string, givenName: string) {
    this.historyService.getHistory(familyName, givenName).subscribe({
      next: (response) => {
        console.log(response);
        this.getHistory = response;
        this.getNotes = this.getHistory.notes;
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    })
  }

  public onAddHistory(addForm: NgForm) {
    document.getElementById('add-history-form')?.click();
    this.historyService.addHistory(addForm.value).subscribe({
      next: (response) => {
        console.log(response);
        this.getPatientByName(response.familyName, response.givenName);
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    })
  }

  public onAddNote(addForm: NgForm) {
    document.getElementById('add-note-form')?.click();
    this.historyService.addNote(addForm.value).subscribe({
      next: (response) => {
        console.log(response);
        this.getPatientByName(response.familyName, response.givenName);
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    })
  }

  public onUpdateNote(editNoteForm: NgForm) {
    document.getElementById('edit-note-form')?.click();
    this.historyService.updateNote(editNoteForm.value).subscribe({
      next: (response: History) => {
        console.log(response);
        this.getPatientByName(response.familyName, response.givenName);
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    })
  }
}
