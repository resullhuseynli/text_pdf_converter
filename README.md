TXT to PDF Converter
A simple web application that allows users to convert .txt files into downloadable .pdf files.

Description
This web app allows users to select a .txt file, upload it to the server, and receive a downloadable .pdf version of the file. It uses a front-end HTML form for file selection and communicates with a back-end server (assumed to be running locally at localhost:8080/convert) to perform the conversion.

Features
Upload .txt files for conversion

Receive the converted file as a downloadable .pdf

Basic user feedback through status messages

Requirements
A server running at http://localhost:8080/convert that can handle the file upload and conversion.

A modern web browser (Google Chrome, Firefox, etc.)

Installation
Clone the repository or download the project files.

Set up a backend server that handles file conversion requests at http://localhost:8080/convert. (The server should receive the .txt file, convert it to .pdf, and return the .pdf file as a response.)

Open the index.html file in a browser to start using the app.

Usage
Click the Choose File button to select a .txt file from your device.

Click the Convert to PDF button to upload the file and start the conversion process.

Once the conversion is complete, the PDF file will automatically download.

JavaScript Functionality
The uploadFile function is triggered when the "Convert to PDF" button is clicked.

It checks if a file is selected and sends it to the backend server using the fetch API.

Upon a successful response, the .pdf file is automatically downloaded to the user's device.

Error Handling
The application provides feedback messages to the user during the process.

If no file is selected, an error message will appear.

If the server encounters an issue, the user will be notified of the failure.

Technologies Used
HTML5

CSS (linked in style.css)

JavaScript (fetch API for file upload)

