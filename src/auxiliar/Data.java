package auxiliar;

public class Data {

    private int dia;
    private int mes;
    private int ano;

    public Data(int dia, int mes, int ano) {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }

    public Data createData(int dia, int mes, int ano){
        if ((mes>0 && mes<13)&&(dia>0 && dia <32)&& String.valueOf(ano).length() == 4){
            if (mes== 2){
                if (dia<30){
                    return new Data(dia,mes,ano);
                }
                return null;
            }
            if  (mes == 4 || mes == 6 || mes == 9 || mes == 11){
                if (dia > 30){
                    return null;
                }
                return new Data(dia,mes,ano);
            }
            return new Data(dia,mes,ano);

        }
        return null;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}
