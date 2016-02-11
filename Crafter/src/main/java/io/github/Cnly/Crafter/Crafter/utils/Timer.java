package io.github.Cnly.Crafter.Crafter.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class Timer
{
    
    private Plugin plugin;
    
    private long goal;
    private long step;
    private long delay;
    private long interval;
    private boolean repeat;
    private long start;
    private long current;
    
    private TimerListener listener;
    private BukkitTask task;
    
    public Timer(Plugin plugin, long goal, long step, long delay, long interval, long start, boolean repeat, TimerListener listener)
    {
    
        if(step == 0)
        {
            throw new IllegalArgumentException("Step could not be zero!");
        }
    
        if(step > 0 && start > goal)
        {
            throw new IllegalArgumentException("Step > 0 && start > goal!");
        }
    
        if(step < 0 && start < goal)
        {
            throw new IllegalArgumentException("Step < 0 && start < goal!");
        }
        
        this.plugin = plugin;
        this.goal = goal;
        this.step = step;
        this.delay = delay;
        this.interval = interval;
        this.start = start;
        this.repeat = repeat;
        this.listener = listener;
        
    }
    
    public Timer(Plugin plugin, long goal, long step, long start, boolean repeat, TimerListener listener)
    {
        this(plugin, goal, step, 20, 20, start, repeat, listener);
    }
    
    public Timer(Plugin plugin, long start, boolean repeat, TimerListener listener)
    {
        this(plugin, 0, -1, start, repeat, listener);
    }
    
    public void start()
    {
        
        current = start;
        this.listener.onStart(this);
        
        this.task = Bukkit.getScheduler().runTaskTimer(this.plugin, new Runnable()
        {
            
            @Override
            public void run()
            {
                
                current += step;
                
                if(step > 0)
                {
                    
                    if(current >= goal)
                    {
                        reachGoal();
                    }
                    else
                    {
                        listener.onTick(Timer.this);
                    }
                    
                }
                else
                {
                    
                    if(current <= goal)
                    {
                        reachGoal();
                    }
                    else
                    {
                        listener.onTick(Timer.this);
                    }
                    
                }
                
            }
            
        }, this.delay, this.interval);
        
    }
    
    private void reachGoal()
    {
    
        current = goal;
        listener.onReachGoal(Timer.this);
    
        if(repeat)
        {
            current = start;
            listener.onRepeatStart(Timer.this);
        }
        else
        {
            Timer.this.stop();
        }
        
    }
    
    public void stop()
    {
        if(this.task != null)
        {
            this.task.cancel();
            this.listener.onStop(this);
        }
    }
    
    public Plugin getPlugin()
    {
        return plugin;
    }
    
    public long getGoal()
    {
        return goal;
    }
    
    public long getDelay()
    {
        return delay;
    }
    
    public long getInterval()
    {
        return interval;
    }
    
    public long getStep()
    {
        return step;
    }
    
    public boolean isRepeat()
    {
        return repeat;
    }
    
    public long getStart()
    {
        return start;
    }
    
    public long getCurrent()
    {
        return current;
    }
    
    public static class TimerListener
    {
    
        public void onTick(Timer timer){}
        
        public void onReachGoal(Timer timer){}
        
        public void onStart(Timer timer){}
        
        public void onRepeatStart(Timer timer){}
        
        public void onStop(Timer timer){}
        
    }
    
}
