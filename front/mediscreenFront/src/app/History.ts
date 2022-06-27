export interface Note {
  date: Date,
  text: string
}

export interface History {
  patientId: number,
  familyName: string,
  givenName: string,
  notes:Note[]
}
