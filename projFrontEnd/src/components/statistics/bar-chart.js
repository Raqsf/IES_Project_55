import { Bar } from "react-chartjs-2";

export const BarChart = ({ chartData }) => {
  return (
    <>
    <div>
      <Bar
        data={chartData}
        options={{
            title:{
            display:true,
            text:'Average Rainfall per month',
            fontSize:20
            },
            legend:{
            display:true,
            position:'right'
            }
        }}
        />
    </div>
    </>
  );
};