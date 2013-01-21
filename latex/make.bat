del /s /f *.ps *.dvi *.aux *.toc *.idx *.ind *.ilg *.log *.out *.brf *.blg *.bbl main.pdf

pdflatex main
echo ----
makeindex main.idx
echo ----
pdflatex main

setlocal enabledelayedexpansion
set count=5
:repeat
set content=X
for /F "tokens=*" %%T in ( 'findstr /C:"Rerun LaTeX" main.log' ) do set content="%%~T"
if !content! == X for /F "tokens=*" %%T in ( 'findstr /C:"Rerun to get cross-references right" main.log' ) do set content="%%~T"
if !content! == X goto :skip
set /a count-=1
if !count! EQU 0 goto :skip

echo ----
pdflatex main
goto :repeat
:skip
endlocal
